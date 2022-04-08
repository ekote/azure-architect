### For the event producer install on cluster:
- pip install azure-eventhub
- pip install azure-eventhub-checkpointstoreblob-aio
- import libs:
 ```
 import asyncio
from azure.eventhub.aio import EventHubProducerClient
from azure.eventhub import EventData
from random import randrange, random, choice
from datetime import datetime
import json
import time
```


## Synapse -> 

## FAQ
1. Structured streaming on PROD https://docs.databricks.com/spark/latest/structured-streaming/production.html
2. By default, streams run in append mode `outputMode("append")`
3. Write the streaming to a table - use `toTable` method in Spark 3.1+ 
```
flatSchemaDF
  .writeStream
  .outputMode("append")
  .option("checkpointLocation", checkpointPath)
  .toTable("temp")
```

then read `spark.read.table("temp")`

