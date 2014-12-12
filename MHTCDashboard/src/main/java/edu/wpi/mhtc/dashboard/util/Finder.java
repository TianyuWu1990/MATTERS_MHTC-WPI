/**
 * Sample code that finds files that match the specified glob pattern.
 * For more information on what constitutes a glob pattern, see
 * http://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob
 *
 * The file or directories that match the pattern are printed to
 * standard out.  The number of matches is also printed.
 *
 * When executing this application, you must put the glob pattern
 * in quotes, so the shell will not expand any wild cards:
 *              java Find . -name "*.java"
 */
package edu.wpi.mhtc.dashboard.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;

public class Finder extends SimpleFileVisitor<Path> {

		private final PathMatcher matcher;
		private Path endFile;

		public Finder(String pattern) {
			matcher = FileSystems.getDefault()
					.getPathMatcher("glob:" + pattern);
		}

		// Compares the glob pattern against
		// the file or directory name.
		public void find(Path file) {
			Path name = file.getFileName();
			if (name != null && matcher.matches(name)) {
				endFile = file;
			}
		}

		// Invoke the pattern matching
		// method on each file.
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			find(file);
			return CONTINUE;
		}

		// Invoke the pattern matching
		// method on each directory.
		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) {
			find(dir);
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			System.err.println(exc);
			return CONTINUE;
		}
		
		public Path done() {
			return endFile;
		}
}