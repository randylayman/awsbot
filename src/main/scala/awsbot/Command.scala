package awsbot

import com.ep.hippyjava.model.Room

trait Command {

  def matchStrength(): MatchStrength.Value
  def execute(user: String, room: Room, bot: AWSBot)  
}