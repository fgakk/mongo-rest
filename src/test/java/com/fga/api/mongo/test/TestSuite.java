package com.fga.api.mongo.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@RunWith(value = Suite.class)
@SuiteClasses({ TestConnection.class, TestCRUD.class })
public class TestSuite {

	private static final MongodStarter starter = MongodStarter
			.getDefaultInstance();

	private static MongodExecutable _mongodExe;
	private static MongodProcess _mongod;

	@BeforeClass
	public static void setUp() throws Exception {

		_mongodExe = starter.prepare(new MongodConfigBuilder()
				.version(Version.Main.PRODUCTION)
				.net(new Net(27017, Network.localhostIsIPv6())).build());
		_mongod = _mongodExe.start();

	}

	@AfterClass
	public static void tearDown() throws Exception {

		_mongod.stop();
		_mongodExe.stop();
	}
}
