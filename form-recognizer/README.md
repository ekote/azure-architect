## Azure Form Recognizer

- [What is Form Recognizer?](https://docs.microsoft.com/en-us/azure/cognitive-services/form-recognizer/overview)
- Easily extract text, keys, and tables from your documents 
- Custom or pre-built models 
- Especially useful if you have a significant amount of form types
- Only a handful of sample documents needed to train a model, get started with just 5 forms
- Tiff support
- Extraction enhancements - Latest and greatest OCR support, extraction improvements and more 
- Async and Batch - Support for Async and Batch calls for training and analyzing large data sets 
   and files
- Steps:
  - (optional) Label
  - Train
  - Analyze

![image-20200902173720729](images\image-20200902173720729.png)

![2020-09-02_17h38_54](images\2020-09-02_17h38_54.png)

## Benefits with Form Recognizer

- Save significant amounts of time on repetitive document reading and data entry

- Automate workflows from start to finish by adding intelligent decision making

- Find and escalate anomalous documents to a user for review

- Reduce time and cost, allowing employees to focus on more important tasks

- Eliminate data entry error

- Speed data entry

- Utilize tools (camera) that everybody has today

- Auto generate expense receipt

- Quick and easy to build

  

# Video demo 



<video src=images\video-form-reco-demo.mp4"></video>



## DIY

Objective A: recognize text on the image.



Steps:

1. Go to the Azure portal and [create a new Form Recognizer resource ](https://ms.portal.azure.com/#create/Microsoft.CognitiveServicesFormRecognizer). 
2. Prepare the dataset with the sample data. Create a storage account then create a container and upload there your data
3. Go to [FOTT](https://fott.azurewebsites.net/) and create New Project. Connect with your storage account.  
4. Tags Editor:
   1. Form Recognizer runs a OCR on your image/document. You can create a tag
5. Train a new model:
6. Analyze via a script or via image uploading
7. Train



#### Steps

![2020-09-02_18h05_59](images\2020-09-02_18h05_59.png)

![2020-09-02_18h05_23](images\2020-09-02_18h05_23.png)



## Form Recognizer inside container (security, IoT, IoT on Edge) 

- https://docs.microsoft.com/en-us/azure/cognitive-services/form-recognizer/form-recognizer-container-howto





## FAQ

1. Custom model or pre-built? 
   1. depends on your needs - there are just a couple of pre-built models. Moreover, see the differences in pricing.
2. What is the alternative tool? 
   1. Cognitive Services -> Computer Vision ->  OCR



## Links

- https://docs.microsoft.com/en-us/azure/cognitive-services/form-recognizer/form-recognizer-container-howto

- https://docs.microsoft.com/en-us/azure/cognitive-services/form-recognizer/
- https://westus2.dev.cognitive.microsoft.com/docs/services/form-recognizer-api-v2-preview/operations/AnalyzeWithCustomForm
- https://westus2.dev.cognitive.microsoft.com/docs/services/form-recognizer-api-v2-preview/operations/TrainCustomModelAsync
- https://docs.microsoft.com/en-us/azure/cognitive-services/form-recognizer/form-recognizer-container-howto 
- https://fott.azurewebsites.net/
- https://www.customvision.ai/-
- https://github.com/ekote/OCR
- https://azure.microsoft.com/en-in/services/cognitive-services/form-recognizer/
- https://docs.microsoft.com/en-us/azure/cognitive-services/computer-vision/vision-api-how-to-topics/howtoanalyzevideo_vision