// Databricks notebook source
// MAGIC %md
// MAGIC 
// MAGIC ### Objective
// MAGIC 
// MAGIC - Dataset from https://github.com/Microsoft/BotBuilder-PersonalityChat/tree/master/CSharp/Datasets
// MAGIC - Import dataset to Azure SQL Database
// MAGIC   - Microsoft SQL Management Studio > Connect with db > Task > Import 

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ## SQL Databases using the Apache Spark connector
// MAGIC - Why? It's faster > `It can outperform row-by-row insertion with 10x to 20x faster performance`
// MAGIC - Docs - https://docs.databricks.com/data/data-sources/sql-databases-azure.html
// MAGIC - To run that, we have to install maven package - com.microsoft.azure:azure-sqldb-spark:1.0.2.
// MAGIC 
// MAGIC ![Spark connector](https://docs.microsoft.com/en-us/azure/sql-database/media/sql-database-spark-connector/architecture.png "Spark connector")

// COMMAND ----------

import com.microsoft.azure.sqldb.spark.config.Config
import com.microsoft.azure.sqldb.spark.connect._

val config = Config(Map(
  "url"            -> "dbnamehere.database.windows.net",
  "databaseName"   -> "dbnamehere",
  "dbTable"        -> "dbo.tablenamehere",
  "user"           -> "username@dbnamehere",
  "password"       -> "#######",
  "connectTimeout" -> "5",
  "queryTimeout"   -> "5" 
))


val collection = sqlContext.read.sqlDB(config)
collection.show()

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ## Magic here
// MAGIC 
// MAGIC ![magic](https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fdam%2Fimageserve%2F1160614309%2F960x0.jpg%3Ffit%3Dscale "magic")
// MAGIC 
// MAGIC - transofrm your dataset, save and then prepare the body to send to knowledge base 

// COMMAND ----------

val wsad = collection.select("Question", "Answer")

wsad.show()

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ## Knowledge base
// MAGIC - https://www.qnamaker.ai/
// MAGIC - create knowledge base via e.g. UI
// MAGIC - https://docs.microsoft.com/en-us/azure/cognitive-services/qnamaker/quickstarts/quickstart-sdk?pivots=programming-language-python

// COMMAND ----------

// example how to run curl from dbx
%sh
curl https://HERE_YOUR_SPECIFIC_NAME.cognitiveservices.azure.com/qnamaker/v4.0/knowledgebases/KNOWLEDGE_BASE_ID \
-X GET \
-H "Ocp-Apim-Subscription-Key: HERE_KEY"

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ## Databricks - Knowledge base connection example via API
// MAGIC - Docs https://westus.dev.cognitive.microsoft.com/docs/services/5a93fcf85b4ccd136866eb37/operations/knowledgebases_getknowledgebasedetails/console

// COMMAND ----------

// MAGIC %python
// MAGIC 
// MAGIC import http.client, urllib.request, urllib.parse, urllib.error, base64, json
// MAGIC 
// MAGIC headers = {
// MAGIC     'Content-Type': 'application/json',
// MAGIC     'Ocp-Apim-Subscription-Key': 'key_here',
// MAGIC }
// MAGIC 
// MAGIC params = urllib.parse.urlencode({
// MAGIC })
// MAGIC 
// MAGIC # build a proper body
// MAGIC body = json.dumps({
// MAGIC   "add": {
// MAGIC     "qnaList": [
// MAGIC       {
// MAGIC         "id": 0,
// MAGIC         "answer": "You can change the default message if you use the QnAMakerDialo",
// MAGIC         "source": "Custom Editorial",
// MAGIC         "questions": [
// MAGIC           "How can I change the default message from QnA Maker?"
// MAGIC         ],
// MAGIC         "metadata": []
// MAGIC       }
// MAGIC     ],
// MAGIC   }
// MAGIC })
// MAGIC 
// MAGIC try:
// MAGIC     conn = http.client.HTTPSConnection('westus.api.cognitive.microsoft.com')
// MAGIC     conn.request("PATCH", "/qnamaker/v4.0/knowledgebases/{here_knowledge_base_ID}?%s" % params, body, headers)
// MAGIC     response = conn.getresponse()
// MAGIC     data = response.read()
// MAGIC     print(data)
// MAGIC     conn.close()
// MAGIC except Exception as e:
// MAGIC     print("[Errno {0}] {1}".format(e.errno, e.strerror))

// COMMAND ----------

// take "operationId" from the response

// COMMAND ----------

// MAGIC %python
// MAGIC 
// MAGIC import http.client, urllib.request, urllib.parse, urllib.error, base64, json
// MAGIC 
// MAGIC headers = {
// MAGIC     # Request headers
// MAGIC     'Ocp-Apim-Subscription-Key': 'key_here',
// MAGIC }
// MAGIC 
// MAGIC params = urllib.parse.urlencode({
// MAGIC })
// MAGIC 
// MAGIC body = urllib.parse.urlencode({
// MAGIC })
// MAGIC 
// MAGIC try:
// MAGIC     conn = http.client.HTTPSConnection('westus.api.cognitive.microsoft.com')
// MAGIC     conn.request("GET", "/qnamaker/v4.0/operations/{operationId}?%s" % params, body, headers)
// MAGIC     response = conn.getresponse()
// MAGIC     data = response.read()
// MAGIC     print(data)
// MAGIC     conn.close()
// MAGIC except Exception as e:
// MAGIC     print("[Errno {0}] {1}".format(e.errno, e.strerror))

// COMMAND ----------

// you can see that "operationState": "Succeeded" -> so check the https://www.qnamaker.ai/Edit/KnowledgeBase?kbId=here_your_knowledge_base_id

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ## Additional
// MAGIC - API: https://westus.dev.cognitive.microsoft.com/docs/services/5a93fcf85b4ccd136866eb37/operations/5ac266295b4ccd1554da7600
// MAGIC - Ref: https://docs.microsoft.com/en-us/rest/api/cognitiveservices/qnamaker/knowledgebase/update
