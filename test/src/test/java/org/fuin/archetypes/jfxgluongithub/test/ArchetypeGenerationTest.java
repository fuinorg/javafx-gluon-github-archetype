package org.fuin.archetypes.jfxgluongithub.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.shared.verifier.VerificationException;
import org.apache.maven.shared.verifier.Verifier;
import org.fuin.marchetyper.core.Config;
import org.fuin.marchetyper.core.ConfigImpl;
import org.fuin.utils4j.fileprocessor.FileHandlerResult;
import org.fuin.utils4j.fileprocessor.FileProcessor;
import org.junit.jupiter.api.Test;

/**
 * Tests the archetype generation.
 */
public class ArchetypeGenerationTest {

	@Test
	public void testGenerate() throws VerificationException, IOException {

		// PREPARE
		final File configFile = new File("../marchetyper/marchetyper-config.xml");
		final Config config = ConfigImpl.load(configFile);
		final File tmpDir = new File("target/" + this.getClass().getSimpleName());
		if (tmpDir.exists()) {
			FileUtils.deleteDirectory(tmpDir);
		}
		tmpDir.mkdirs();

		final Verifier generateVerifier = new Verifier(tmpDir.getAbsolutePath());
		generateVerifier.setAutoclean(false);

		final List<String> args = new ArrayList<>();
		args.add("archetype:generate");
		args.add("-DarchetypeGroupId=" + config.getArchetype().getGroupId());
		args.add("-DarchetypeArtifactId=" + config.getArchetype().getArtifactId());
		args.add("-DarchetypeVersion=" + config.getArchetype().getVersion());
		args.add("-DpkgName=com.example.archetypes.myapp");
		args.add("-DappName=MyApp");
		args.add("-DappDescription=My cool application");
		args.add("-DinteractiveMode=false");

		generateVerifier.addCliArguments(args.toArray(new String[args.size()]));

		// TEST
		generateVerifier.execute();

		// VERIFY
		generateVerifier.verifyErrorFreeLog();
		
		final String[] keywords = { "Hello" };
		final List<File> found = findTextFilesContainingKeywords(tmpDir, config, keywords);		
		if (!found.isEmpty() && !onlyExcludedFiles(found, "target/ArchetypeGenerationTest/abc-myapp/README.md")) {			                                               
			throw new VerificationException("The following file(s) contain at least one of the keywords: "
					+ Arrays.asList(keywords) + "\n" + found);
		}


	}

	private static List<File> findTextFilesContainingKeywords(final File dir, final Config config, final String... keywords) {

		final List<File> found = new ArrayList<>();
		new FileProcessor(file -> {

			if (config.isText(file) && (config.includes(file) || !config.excludes(file))) {
				String text;
				try {
					text = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
				} catch (IOException ex) {
					throw new RuntimeException("Failed to read: " + file, ex);
				}
				if (StringUtils.containsAny(text, keywords)) {
					found.add(file);
				}
			}

			return FileHandlerResult.CONTINUE;
		}).process(dir);
		return found;

	}

	private static boolean onlyExcludedFiles(final List<File> foundFiles, final String... excludes) {
		final List<File> excludedFiles = new ArrayList<>();
		for (final String exclude : excludes) {
			excludedFiles.add(new File(exclude.replace('/', File.separatorChar)));
		}
		final List<File> differences = new ArrayList<>(foundFiles);
		differences.removeAll(excludedFiles);
		return differences.isEmpty();
	}

}
