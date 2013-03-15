package awsbot

import com.amazonaws.services.ec2.model.IpPermission
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest
import com.amazonaws.auth.PropertiesCredentials
import java.io.FileInputStream
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.ec2.model.RevokeSecurityGroupIngressRequest

object AmazonSecurityRules {

  var ec2Client:AmazonEC2Client = null
  
  def initialize(configFile: String): Unit = {
    val credentials = new PropertiesCredentials(new FileInputStream(configFile))
    ec2Client = new AmazonEC2Client(credentials)
    AmazonSecurityRuleStorage.initialize(configFile)
  }
  
  def getAddressesForUser(user: String): Seq[String] = {
    AmazonSecurityRuleStorage.addressForUser(user)
  }
  
  private def buildIpPermissions(address: String): IpPermission = {
    new IpPermission().withIpRanges(address + "/32").withIpProtocol("tcp").withFromPort(0).withToPort(65535)
  }
  
  private def removeAddress(user: String, address: String) = {
    val request = new RevokeSecurityGroupIngressRequest().withGroupName("HDAP_Voice").withIpPermissions(buildIpPermissions(address))
    ec2Client.revokeSecurityGroupIngress(request)
    AmazonSecurityRuleStorage.removeAddressForUser(user, address)
  }
  
  //Returns old address(es) for user if there were any
  def addAddressForUser(user: String, address: String): Seq[String] = {
    val oldAddresses = AmazonSecurityRuleStorage.addressForUser(user)
    oldAddresses.foreach(addr => removeAddress(user, addr))
    AmazonSecurityRuleStorage.addAddressForUser(user, address)
    
    val request = new AuthorizeSecurityGroupIngressRequest().withGroupName("HDAP_Voice").withIpPermissions(buildIpPermissions(address))
    ec2Client.authorizeSecurityGroupIngress(request)
    
    oldAddresses
  }
}