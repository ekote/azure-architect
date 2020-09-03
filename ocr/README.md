
## Azure Cognitive Services API - OCR build in Azure Functions in Python

[API ref 3.0 preview](https://westus2.dev.cognitive.microsoft.com/docs/services/5d98695995feb7853f67d6a6/operations/5d986960601faab4bf452005
)

[API ref 2.1](https://westus2.dev.cognitive.microsoft.com/docs/services/5d98695995feb7853f67d6a6/operations/5d986960601faab4bf452005
)

[API - form recogni](https://docs.microsoft.com/en-us/azure/cognitive-services/form-recognizer/)


1. Start this function locally via `func start`
2. Trigger the funtion
```ss
curl http://localhost:7071/api/OcrAzure?url=https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Atomist_quote_from_Democritus.png/338px-Atomist_quot
e_from_Democritus.png
```


## pricing
- free - 5,000 included transactions.
- s1 - 1000 transactions -> 1.50$

OCR:
- 0-1M transactions — $1.50 per 1,000 transactions
- 1M-5M transactions — $1 per 1,000 transactions
- 5M-10M transactions — $0.65 per 1,000 transactions
- 10M-100M transactions — $0.65 per 1,000 transactions
- 100M+ transactions — $0.65 per 1,000 transactions 

ref: https://azure.microsoft.com/en-us/pricing/details/cognitive-services/
