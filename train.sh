#!/bin/bash

export LD_LIBRARY_PATH="${LD_LIBRARY_PATH}:."
java -classpath "bin:lib/trove.jar" -Xmx32000m parser.DependencyParser model-file:ewt_standard_labeled.model train train-file:/home/peter/Documents/uni/scriptie/en_ewt-ud-train.conllu test test-file:/home/peter/Documents/uni/scriptie/en_ewt-ud-test.conllu label:true
