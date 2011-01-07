#!/bin/bash

#==============================================================#
# autor: Michał Skrzyński                                      #
# Skrypt uruchamiający klasę javy z aktualnego katalogu.       #
# Ustawia CLASSPATH na katalogi:                               #
#     * aktualny                                               #
#     * bin                                                    #
#     * build/classes                                          #
#     * wszystkie pliki z katalogów lib*/*                     #
# użycie: ./@ pakiet1.Foo                                      #
#==============================================================#

runclass(){
  cp=".:bin:classes:build/classes"

  for s in `ls -d1 lib*/*`;do
    cp="$cp:$s"
  done

  java -cp $cp $@
}
runclass $@
