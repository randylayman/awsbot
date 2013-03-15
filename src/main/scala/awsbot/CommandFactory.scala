package awsbot

trait CommandFactory {

    def accept(input: String): Command
    def helpText: String
}