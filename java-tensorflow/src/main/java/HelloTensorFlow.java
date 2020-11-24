import org.tensorflow.*;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Constant;
import org.tensorflow.op.core.ReduceSum;
import org.tensorflow.op.math.Add;
import org.tensorflow.op.math.Div;
import org.tensorflow.op.math.Mul;
import org.tensorflow.types.TFloat64;

import java.util.Arrays;

public class HelloTensorFlow {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello TensorFlow " + TensorFlow.version());

        double[] pi = new double[1_000_000];
        Arrays.fill(pi, 3.14); // Pi
        double[] e = new double[1_000_000];
        Arrays.fill(e, 2.72); // e

        try (Graph g = new Graph(); Session s = new Session(g)) {
            Ops tf = Ops.create(g);

            // Tensor<TFloat64> aTensor = TFloat64.scalarOf(3.456);
            Constant<TFloat64> a = tf.constant(3.456);
            Constant<TFloat64> b = tf.constant(0.5);
            Constant<TFloat64> x = tf.constant(pi);
            Constant<TFloat64> y = tf.constant(e);

            // x * y * a / x + b where a = 3.456 and b = 0.5
            // Operand<TFloat64> z = tf.reduceSum(tf.math.add(tf.math.div(tf.math.mul(tf.math.mul(x, y), a), x), b), tf.constant(0));

            String device = "/CPU:0"; // "/GPU:0", "/CPU:0";
            GraphOperation xy = g.opBuilder(Mul.OP_NAME, "xy").setDevice(device).addInput(x.asOutput()).addInput(y.asOutput()).build();
            GraphOperation xya = g.opBuilder(Mul.OP_NAME, "xya").setDevice(device).addInput(xy.output(0)).addInput(a.asOutput()).build();
            GraphOperation xyax = g.opBuilder(Div.OP_NAME, "xyax").setDevice(device).addInput(xya.output(0)).addInput(x.asOutput()).build();
            GraphOperation xyaxb = g.opBuilder(Add.OP_NAME, "xyaxb").setDevice(device).addInput(xyax.output(0)).addInput(b.asOutput()).build();
            GraphOperation sum = g.opBuilder(ReduceSum.OP_NAME, "sum").setDevice(device).addInput(xyaxb.output(0)).addInput(tf.constant(0).asOutput()).build();

            long startTime = System.nanoTime();
            double result = 0.0;
            for (int i = 0; i < 1000; i++) {
                // Tensor<TFloat64> tensor = (Tensor<TFloat64>) s.runner().fetch(z).run().get(0);
                Tensor<TFloat64> tensor = (Tensor<TFloat64>) s.runner().fetch("sum").run().get(0);
                result = tensor.data().getDouble();
                tensor.close();
            }
            long stopTime = System.nanoTime();
            System.out.println(result);
            System.out.println("Time [s.] " + (double) (stopTime - startTime) / 1000_000_000);
        }

        System.out.println("Bye bye");
    }
}
