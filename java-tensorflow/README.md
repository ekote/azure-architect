
- to compile:```mvn -q compile exec:java```
- results from local PL:
```ekot@DESKTOP-GEGKBHS:/mnt/c/dev/azure-architect/java-tensorflow$ mvn -q compile exec:java
   2020-11-09 23:38:39.921761: W external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:59] Could not load dynamic library 'libcudart.so.10.1'; dlerror: libcudart.so.10.1: cannot open shared object file: No such file or directory
   2020-11-09 23:38:39.923927: I external/org_tensorflow/tensorflow/stream_executor/cuda/cudart_stub.cc:29] Ignore above cudart dlerror if you do not have a GPU set up on your machine.
   Hello TensorFlow 2.3.1
   2020-11-09 23:38:40.210367: I external/org_tensorflow/tensorflow/core/platform/cpu_feature_guard.cc:142] This TensorFlow binary is optimized with oneAPI Deep Neural Network Library (oneDNN)to use the following CPU instructions in performance-critical operations:  AVX2 FMA
   To enable them in other operations, rebuild TensorFlow with the appropriate compiler flags.
   2020-11-09 23:38:40.212846: W external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:59] Could not load dynamic library 'libcuda.so.1'; dlerror: libcuda.so.1: cannot open shared object file: No such file or directory
   2020-11-09 23:38:40.213076: W external/org_tensorflow/tensorflow/stream_executor/cuda/cuda_driver.cc:312] failed call to cuInit: UNKNOWN ERROR (303)
   2020-11-09 23:38:40.213354: I external/org_tensorflow/tensorflow/stream_executor/cuda/cuda_diagnostics.cc:156] kernel driver does not appear to be running on this host (DESKTOP-GEGKBHS): /proc/driver/nvidia/version does not exist
   10 doubled is 20
```

- results from Data science vm with gpu
```
ekot@ekot-dsvm-gpu:~/azure-architect/java-tensorflow$ mvn -q compile exec:java
2020-11-09 22:41:37.478919: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcudart.so.10.1
org.tensorflow.NativeLibrary: isLoaded: true
Hello TensorFlow 2.3.1
org.tensorflow.NativeLibrary: isLoaded: true
2020-11-09 22:41:37.685269: I external/org_tensorflow/tensorflow/core/platform/cpu_feature_guard.cc:142] This TensorFlow binary is optimized with oneAPI Deep Neural Network Library (oneDNN)to use the following CPU instructions in performance-critical operations:  AVX2 FMA
To enable them in other operations, rebuild TensorFlow with the appropriate compiler flags.
2020-11-09 22:41:37.687055: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcuda.so.1
2020-11-09 22:41:37.780166: I external/org_tensorflow/tensorflow/core/common_runtime/gpu/gpu_device.cc:1716] Found device 0 with properties:
pciBusID: 0001:00:00.0 name: Tesla V100-PCIE-16GB computeCapability: 7.0
coreClock: 1.38GHz coreCount: 80 deviceMemorySize: 15.78GiB deviceMemoryBandwidth: 836.37GiB/s
2020-11-09 22:41:37.780208: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcudart.so.10.1
2020-11-09 22:41:37.782171: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcublas.so.10
2020-11-09 22:41:37.783982: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcufft.so.10
2020-11-09 22:41:37.784314: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcurand.so.10
2020-11-09 22:41:37.786486: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcusolver.so.10
2020-11-09 22:41:37.787686: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcusparse.so.10
2020-11-09 22:41:37.791900: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcudnn.so.7
2020-11-09 22:41:37.794988: I external/org_tensorflow/tensorflow/core/common_runtime/gpu/gpu_device.cc:1858] Adding visible gpu devices: 0
2020-11-09 22:41:37.795031: I external/org_tensorflow/tensorflow/stream_executor/platform/default/dso_loader.cc:48] Successfully opened dynamic library libcudart.so.10.1
2020-11-09 22:41:38.537540: I external/org_tensorflow/tensorflow/core/common_runtime/gpu/gpu_device.cc:1257] Device interconnect StreamExecutor with strength 1 edge matrix:
2020-11-09 22:41:38.537592: I external/org_tensorflow/tensorflow/core/common_runtime/gpu/gpu_device.cc:1263]      0
2020-11-09 22:41:38.537605: I external/org_tensorflow/tensorflow/core/common_runtime/gpu/gpu_device.cc:1276] 0:   N
2020-11-09 22:41:38.540096: I external/org_tensorflow/tensorflow/core/common_runtime/gpu/gpu_device.cc:1402] Created TensorFlow device (/job:localhost/replica:0/task:0/device:GPU:0 with 14763 MB memory) -> physical GPU (device: 0, name: Tesla V100-PCIE-16GB, pci bus id: 0001:00:00.0, compute capability: 7.0)
org.tensorflow.NativeLibrary: isLoaded: true
10 doubled is 20
```