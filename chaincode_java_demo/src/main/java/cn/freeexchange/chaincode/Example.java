package cn.freeexchange.chaincode;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.List;

import javax.json.Json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class Example extends ChaincodeBase {
	
	private static Log log = LogFactory.getLog(Example.class);
	

	@Override
	public Response init(ChaincodeStub stub) {
		// expects to be called with: { "init", key, value }
		try {

			final List<String> args = stub.getStringArgs();
			if (args.size() != 3) {
				return newErrorResponse("Incorrect number of arguments. Expecting \"init\" plus 2 more.");
			}

			final String key = args.get(1);
			final String value = args.get(2);
			stub.putStringState(key, String.valueOf(value));
		} catch (Throwable t) {
			return newErrorResponse(t);
		}
		return newSuccessResponse();
	}

	@Override
	public Response invoke(ChaincodeStub stub) {
		// expects to be called with: { "invoke"|"query", chaincodeName, key }
		try {
			final String function = stub.getFunction();
			final String[] args = stub.getParameters().stream().toArray(String[]::new);
			
			switch (function) {
			case "invoke":
				return doInvoke(stub, args);
			case "query":
				return doQuery(stub, args);
			default:
				return newErrorResponse(format("Unknown function: %s", function));
			}
		} catch (Throwable e) {
			return newErrorResponse(e);
		}
	}

	private Response doQuery(ChaincodeStub stub, String[] args) {
		if (args.length != 1) throw new IllegalArgumentException("Incorrect number of arguments. Expecting: query(key)");

		final String key = args[0];

		return newSuccessResponse(Json.createObjectBuilder()
				.add("Key", key)
				.add("value", stub.getStringState(key))
				.build().toString().getBytes(UTF_8));
	}

	private Response doInvoke(ChaincodeStub stub, String[] args) {
		final String key = args[0];
		final String value = args[1];

		// get state of the from/to keys
		final String keyState = stub.getStringState(key);
		
		if(keyState == null || keyState.equalsIgnoreCase("")) {
			stub.putStringState(key, value);
			log.info("Put complete.");
			return newSuccessResponse(format("Successfully Upload Assets %s , %s.", key, value));
		}
		
		throw new IllegalArgumentException("Key was exists.");

	}

	public static void main(String[] args) throws Exception {
		new Example().start(args);
	}

}
