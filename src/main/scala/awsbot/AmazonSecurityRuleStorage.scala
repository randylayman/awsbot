package awsbot

import java.io.FileInputStream
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import com.amazonaws.auth.PropertiesCredentials
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient
import com.amazonaws.services.dynamodb.model.AttributeValue
import com.amazonaws.services.dynamodb.model.CreateTableRequest
import com.amazonaws.services.dynamodb.model.DescribeTableRequest
import com.amazonaws.services.dynamodb.model.GetItemRequest
import com.amazonaws.services.dynamodb.model.KeySchema
import com.amazonaws.services.dynamodb.model.KeySchemaElement
import com.amazonaws.services.dynamodb.model.ProvisionedThroughput
import com.amazonaws.services.dynamodb.model.PutItemRequest
import com.amazonaws.services.dynamodb.model.ResourceNotFoundException
import com.amazonaws.services.dynamodb.model.ScanRequest
import com.amazonaws.services.dynamodb.model.Condition
import com.amazonaws.services.dynamodb.model.ComparisonOperator
import com.amazonaws.services.dynamodb.model.DeleteRequest
import com.amazonaws.services.dynamodb.model.Key
import com.amazonaws.services.dynamodb.model.DeleteItemRequest

object AmazonSecurityRuleStorage {

  val TABLE_NAME = "enginering_security_rules"
    
  var db: AmazonDynamoDBClient = null

  private def tableExists(tableName: String): Boolean = {
    val request = new DescribeTableRequest().withTableName(TABLE_NAME)
    try {
      val desc = db.describeTable(request)
      true
    } catch {
      case e: ResourceNotFoundException => false
      case t => throw t
    }
  }
  
  private def createTable(): Unit = {
    val request = new CreateTableRequest().withTableName(TABLE_NAME)
      .withKeySchema(new KeySchema().withHashKeyElement(new KeySchemaElement().withAttributeName("user").withAttributeType("S"))
                                    .withRangeKeyElement(new KeySchemaElement().withAttributeName("address").withAttributeType("S")))
      .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(10L).withWriteCapacityUnits(5L))
      db.createTable(request)
  }
  
  def initialize(configFile: String): Unit = {
    val credentials = new PropertiesCredentials(new FileInputStream(configFile))
    db = new AmazonDynamoDBClient(credentials)
    if (tableExists(TABLE_NAME) == false) {
      createTable
    }
  }
  
  def addAddressForUser(user: String, address: String): Unit = {
    val request = new PutItemRequest(TABLE_NAME, Map("user" -> new AttributeValue(user), "address" -> new AttributeValue(address)).toMap.asJava)
    db.putItem(request)
  }
  
  def removeAddressForUser(user: String, address: String): Unit = {
    val request = new DeleteItemRequest(TABLE_NAME, new Key().withHashKeyElement(new AttributeValue(user)).withRangeKeyElement(new AttributeValue(address)))
    db.deleteItem(request)
  }
  
  def addressForUser(user: String): Seq[String] = {
    val condition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString()).withAttributeValueList(new AttributeValue().withS(user))
    val request = new ScanRequest(TABLE_NAME).withScanFilter(Map("user" -> condition).toMap.asJava)
    val result = db.scan(request)
    if (result.getCount() == 0) {
      List()
    } else {
      result.getItems().toList.map(item => item("address").getS())
    }
  }
}