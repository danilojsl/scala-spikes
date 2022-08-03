#!/bin/bash

JAR_PATH=$1
NLP_PIPELINE=$2

NER="NER"
BERT_EMBEDDINGS="BERT"
CONTEXT_SPELL="CONTEXT-SPELL"
TOKENIZER="TOKENIZER"

if [ "$NLP_PIPELINE" == "$NER" ]
  then
    SOURCE_PATH=$3
    DESTINATION_PATH=$4

    sh "$SPARK_HOME"/bin/spark-submit \
      --master local[*] \
      --class com.pipeline.nlp.ProcessDocuments \
      "$JAR_PATH" \
      "$NLP_PIPELINE" "$SOURCE_PATH" "$DESTINATION_PATH"
fi

if [ "$NLP_PIPELINE" == "$BERT_EMBEDDINGS" ]
  then
    MODEL_PATH=$3
    echo "In BERT_EMBEDDINGS..."
    sh "$SPARK_HOME"/bin/spark-submit \
      --master local[*] \
      --driver-memory 5g \
      --class com.pipeline.nlp.ProcessDocuments \
      "$JAR_PATH" \
      "$NLP_PIPELINE" "$MODEL_PATH"
fi

if [ "$NLP_PIPELINE" == "$CONTEXT_SPELL" ]
then
  sh "$SPARK_HOME"/bin/spark-submit \
        --master local[*] \
        --driver-memory 5g \
        --class com.pipeline.nlp.ProcessDocuments \
        "$JAR_PATH" \
        "$NLP_PIPELINE"
fi

if [ "$NLP_PIPELINE" == "$TOKENIZER" ]
then
  sh "$SPARK_HOME"/bin/spark-submit \
        --master local[*] \
        --driver-memory 5g \
        --class com.pipeline.nlp.ProcessDocuments \
        "$JAR_PATH" \
        "$NLP_PIPELINE"
fi