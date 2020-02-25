package org.stacksnap.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.stacksnap.config.StacksnapConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class TestYamlConfiguration {
	
	public static void main(String ...strings) throws IOException {		
		
		Yaml yaml = new Yaml(new Constructor(StacksnapConfiguration.class));

		StacksnapConfiguration config = yaml.load(Files.readString(Paths.get("stacksnap.yml")));

		System.out.println(config);
		
	}

}
