# COVID dataset transformations using ADF



![image-20201007231158920](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007231158920.png)





## ADF provisioning

- V2, v1 and v2 [comparison table](https://docs.microsoft.com/en-us/azure/data-factory/compare-versions)

- Mapping Data Flow not available in all regions, [availability table](https://docs.microsoft.com/en-us/azure/data-factory/compare-versions)



![image-20201007184553006](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007184553006.png)



- Integration with Azure DevOps and Github - [Source Control in Azure Data Factory](https://docs.microsoft.com/azure/data-factory/source-control#troubleshooting-git-integration).



![image-20201007184939744](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007184939744.png)



- networking 

![image-20201007184949954](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007184949954.png)

- Once your data factory is deployed, go to the resource and click on **Authoring and Monitoring** to open the ADF or via adf.azure.com.

![image-20201007185011884](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007185011884.png)

## 



## Copy public data to ADLS

[![](http://img.youtube.com/vi/mSlTKE7Cfp4/0.jpg)](http://www.youtube.com/watch?v=mSlTKE7Cfp4 "")



## ADF components

![image-20201007231440328](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007231440328.png)



### Copy activity 

![Copy activity overview](https://docs.microsoft.com/en-us/azure/data-factory/media/copy-activity-overview/copy-activity-overview.png)





## Integration runtime

![image-20201007233939693](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007233939693.png)

### Azure IR

- A fully managed, serverless compute

- Run Data Flow. The Spark compute is utilized by Data Flows

- **Managed Virtual Network (preview)**

  Managed Virtual Network + Azure Integration Runtime = connecting to data stores using private link service in private network environment

  

  [![High level architecture of Data Factory managed VNet](https://azurecomcdn.azureedge.net/mediahandler/acomblog/media/Default/blog/6f276bf7-e8c8-4d3a-b285-0e682adf3c7b.png)](https://azurecomcdn.azureedge.net/mediahandler/acomblog/media/Default/blog/a4a6d202-6401-4b31-87fb-ec2eb4dcec91.png)

  

### Self-hosted IR

- can run copy activity between a cloud data stores and a data store in private network

- support data stores that requires bring-your-own driver such as SAP Hana and SAP BW (not supported by Azure IR)

- makes outbound HTTP-based connections to open internet - install it on-premise behind your corporate firewall, or inside a virtual private network

- currently, only the self-hosted IR can by run only on a Windows operating system

- HA and scaling out through a association of a self-hosted integration runtime with multiple on-premises machines or virtual machines in Azure. Up to four nodes(machines) is allowed to be associated with a self-hosted IR.

- Scaling up through increasing the node's limit of concurrent jobs - if processor and available RAM aren't well utilized

   

#### Hybrid scenarios

![własne kanały środowiska Integration Runtime](https://docs.microsoft.com/pl-pl/azure/data-factory/media/data-movement-security-considerations/data-management-gateway-channels.png)


### Azure-SSIS IR

- To lift and shift existing SSIS workload, you can create an Azure-SSIS IR to natively execute SSIS packages.





### Which IR to use?

**Azure integration runtime** when you:

- Copy data between public cloud stores 
- Transform data between cloud stores using data flows 
- Execute activities using cloud stores and services

**Self-hosted integration runtime** when you:

- Copy data between cloud and on-premises stores
- Copy data between on-premises stores
- Execute activities using on-premises stores and services

**Azure-SSIS integration runtime** when you:

- Execute SSIS Packages through Azure Data Factory







## Transform dataset and save result in Azure SQL DB 

[![](http://img.youtube.com/vi/sp32IaQtlzU/0.jpg)](http://www.youtube.com/watch?v=sp32IaQtlzU "")


![2020-10-07_21h12_51](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/2020-10-07_21h12_51.png)





## Pipelines

- Save pipelines as [templates](https://azure.microsoft.com/en-us/blog/get-started-quickly-using-templates-in-azure-data-factory/) - GIT integration (Azure DevOps or GitHub required) 
  - ![image-20201007232844897](https://raw.githubusercontent.com/ekote/azure-architect/master/adf/assets/image-20201007232844897.png)



## Linked services

```json
# AZURE SQL DATABASE EXAMPLE
{
  "name": "AzureSqlLinkedService",
  "properties": {
    "type": "AzureSqlDatabase",
    "typeProperties": {
      "connectionString": "Server=tcp:abc.database.windows.net,1433;Database=EquityDB;User_ID=login; 		  Password=P@ssw0rd;Trusted_Connection=False;Encrypt=True;Connection Timeout=30"
    }
  }
}


# AZURE SQL DATABASE vith connection string inside
{
	"name": "sqldbazure",
	"properties": {
		"annotations": [],
		"type": "AzureSqlDatabase",
		"typeProperties": {
			"connectionString": {
				"type": "AzureKeyVaultSecret",
				"store": {
					"referenceName": "AzureKeyVault1",
					"type": "LinkedServiceReference"
				},
				"secretName": "sql-db-connection-string"
			}
		}
	}
}



# AZURE BLOB STORE EXAMPLE
{
  "name": "StorageLinkedService",
  "properties": {
    "type": "AzureStorage",
    "typeProperties": {
      "connectionString": "DefaultEndpointsProtocol=https;AccountName=abcd;AccountKey=087ubp097guhB*(97g9879"
    }
  }
}


# HTTP Server
{
	"name": "CovidGithubDataset",
	"properties": {
		"annotations": [],
		"type": "HttpServer",
		"typeProperties": {
			"url": "https://raw.githubusercontent.com/ekote/azure-architect/master/adf/total_cases.csv",
			"enableServerCertificateValidation": true,
			"authenticationType": "Anonymous"
		}
	}
}

```





## Links

- [Azure Data Studio - connect with SQL DB](https://docs.microsoft.com/pl-pl/sql/azure-data-studio/quickstart-sql-database?toc=%2Fazure%2Fazure-sql%2Ftoc.json&view=sql-server-ver15)

  