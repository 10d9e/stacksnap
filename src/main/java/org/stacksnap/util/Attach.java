package org.stacksnap.util;

import java.io.File;

import net.bytebuddy.agent.ByteBuddyAgent;

public class Attach {

	public static void main(String[] args) {

		File jarFile = new File("../stacksnap/target/stacksnap-0.0.1-SNAPSHOT.jar");
		
		String processId = "43074";
		ByteBuddyAgent.attach(jarFile, processId);

	}

}
