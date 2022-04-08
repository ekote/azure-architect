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
