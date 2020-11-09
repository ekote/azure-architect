import org.tensorflow.ConcreteFunction;
import org.tensorflow.Signature;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.math.Add;
import org.tensorflow.types.TInt32;

public class HelloTensorFlow {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello TensorFlow " + TensorFlow.version());

        try (ConcreteFunction dbl = ConcreteFunction.create(HelloTensorFlow::dbl);
             Tensor<TInt32> x = TInt32.scalarOf(10);
             Tensor<TInt32> dblX = dbl.call(x).expect(TInt32.DTYPE)) {
            basicOperations();
            System.out.println(x.data().getInt() + " doubled is " + dblX.data().getInt());
        } catch (Exception ignored) {
        }
    }

    private static Signature dbl(Ops tf) {
        Placeholder<TInt32> x = tf.placeholder(TInt32.DTYPE);
        Add<TInt32> dblX = tf.math.add(x, x);
        return Signature.builder().input("x", x).output("dbl", dblX).build();
    }

    private static void basicOperations() {
        // Create a new graph
        GraphBuilder g = new GraphBuilder(new Graph());

        // Basic constant operations
        Output a = g.constant("a", 1);
        Output b = g.constant("b", 3);

        // add sub mul and div operations
        Output c = g.add(a, b);
        Output d = g.sub(a, b);
        Output e = g.mul(a, b);
        Output f = g.div(a, b);

        // create a new session and run the operations
        Session s = new Session(g.graph);
        System.out.println(s.runner().fetch(c.op().name()).run().get(0).intValue());
        System.out.println(s.runner().fetch(d.op().name()).run().get(0).intValue());
        System.out.println(s.runner().fetch(e.op().name()).run().get(0).intValue());
        System.out.println(s.runner().fetch(f.op().name()).run().get(0).intValue());

        // matrix multiplication
        float[][] X = new float[][]{new float[]{1, 2, 3}};
        float[][] Y = new float[][]{new float[]{4}, new float[]{5}, new float[]{6}};

        Output h = g.constant("h", X);
        Output i = g.constant("i", Y);

        Output j = g.matmul(i, h);
        System.out.println(s.runner().fetch(j.op().name()).run().get(0).toString());


        // using placeholder
        Output k = g.placeholder("k", DataType.INT32);
        Output l = g.add(c, k);

        // create a tensor to store the value for k
        Tensor k_value = Tensor.create(10);

        // now feed the value for the placeholder and compute l
        System.out.println(s.runner().feed("k", k_value).fetch(l.op().name()).run().get(0).intValue());
    }
}
