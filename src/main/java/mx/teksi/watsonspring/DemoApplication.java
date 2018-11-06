package mx.teksi.watsonspring;

import java.util.stream.Collectors;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class DemoApplication {
	@Autowired
	protected Discovery discovery;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping(value="/discover")
	public String discover(@RequestParam String query) {
		QueryOptions options = new QueryOptions.Builder("system","news")
		.naturalLanguageQuery(query)
		.build();
		QueryResponse queryResponse = discovery.query(options).execute();
		String titles = queryResponse.getResults().stream()
		.map(r ->(String)r.get("title"))
		.collect(Collectors.joining("\n<p>"));
		return titles;
	}
	
}