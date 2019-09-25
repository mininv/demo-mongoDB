package com.yarm.vlad.demomongoDB;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yarm.vlad.demomongoDB.domain.Telephone;
import com.yarm.vlad.demomongoDB.service.PhoneService;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;
import java.io.IOException;
import java.net.UnknownHostException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @Autowired
    public PhoneService phoneService;
    @Autowired
	protected MockMvc mockMvc;

	protected ObjectMapper mapper;

	private static MongodExecutable mongodExecutable;

	@Autowired
	protected MongoTemplate mongoTemplate;

	@Before
	public void setUp() {
		mapper = new ObjectMapper();
	}

	@After
	public void after() {
		mongoTemplate.dropCollection(Telephone.class);
	}

	/**
	 * Here we are setting up an embedded mongodb instance to run with our
	 * integration tests.
	 *
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@BeforeClass
	public static void beforeClass() throws UnknownHostException, IOException {

		MongodStarter starter = MongodStarter.getDefaultInstance();

		IMongodConfig mongoConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(27017, false)).build();

		mongodExecutable = starter.prepare(mongoConfig);

		try {
			mongodExecutable.start();
		} catch (Exception e) {
			closeMongoExecutable();
		}
	}

	/*@AfterClass
	public static void afterClass() {
		closeMongoExecutable();
	}*/

	private static void closeMongoExecutable() {
		if (mongodExecutable != null) {
			mongodExecutable.stop();
		}
	}

}
