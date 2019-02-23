# json-rpc-parser
A library written in Java that allows to parse messages following the JSON-RPC standard.
<h2>Usage:</h2>
<h3>Parsing:</h3>
Use the class JSONFactory to create the JSONObject with the specfied parameters. The class is able to create request, response and error messages.
<pre>
<code>  import jsonrpc.parser.JSONFactory;
  int startId = 0;
  String[] argsList = {"arg","arg1"};
  String JSONString = JSONFactory.getInstance().getRequest("methodName",argsList,startId).parse(); 
</code></pre>
If the client is not interested in receiving a response from the server the method <code>getNotification("method",args)</code> can be used instead (this sets the message's id field to null signalling that a response is not required).<br>
In order to create a request with named parameters a JSONParamList is required in order to couple the argument names to their values. The operation merges the two arrays into a single map so the order is important.
<pre>
<code>  import jsonrpc.parser.JSONFactory;
  import jsonrpc.parser.JSONParamList;
  int startId = 0;
  String[] argVals = {"val0","val1"};
  String[] argNames = {"name0","name1"};
  JSONParamList jsonList = new JSONParamList(argNames,argValues);
  String JSONString = JSONFactory.getInstance().getRequest("methodName",jsonList,startId).parse();
</code></pre>
The class JSONResponse is used to send a successful response. If the operation wasn't completed successfully is possible to create an error response message by invoking the method <code>JSONFactory.getInstance().getResponseError(code,msg,extraInfo).parse();</code>.<br>
This method can be used within a catch block to automatically create a response error in case the request was malformed.
<pre>
<code>catch(ExceptionDeparse e){
return JSONFactory.getInstance().getResponseError(e).parse();
}
</code></pre>
<h3>Deparsing:</h3>
The deparsing is performed by the class jsonrpc.deparser.Translator. This class returns a DeparsedContainer that stores one or more  (in case of a batch of requests/responses) DeparsedElement.<br>
The deparsedElement class holds a map that links the request or response's fields to their values. For example to extract the method field from a request this is the required code:
<pre>
<code>DeparsedContainer container = Translator().decode(JSONString);
String method = container.getElements()[0].getElement().get("method");
</code></pre>
