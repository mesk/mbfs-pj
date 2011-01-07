#!/bin/bash

#========================================================================#
# autor: Michał Skrzyński                                                #
# Skrypt tworzy w aktualnym katalogu niezbędne pliki                     #
# aby katalog był odczytywany przez środowisko eclipse jako projekt javy #
#                                                                        #
# użycie: ./mp                                                           #
#==========================##============================================#

project(){
  echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<projectDescription>
	<name>$1</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.jdt.core.javabuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.jdt.core.javanature</nature>
	</natures>
</projectDescription>" >.project
}

classPath(){
  out="<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<classpath>"

  for s in `ls -d1 src/*`;do
    out="$out
    <classpathentry kind=\"src\" path=\"$s\"/>"
  done
  out="$out
    <classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER\"/>"
  for s in `ls -d1 lib*/*`;do
    out="$out
    <classpathentry kind=\"lib\" path=\"$s\"/>"
  done

  out="$out
</classpath>"

  echo "$out" >.classpath
}

project `pwd|sed 's/.*\///'`
classPath
