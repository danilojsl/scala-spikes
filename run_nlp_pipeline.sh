#!/bin/bash

JAR_PATH=$1
NLP_PIPELINE=$2

NER="NER"
BERT_EMBEDDINGS="BERT"
CONTEXT_SPELL="CONTEXT-SPELL"
TOKENIZER="TOKENIZER"
LIGHT="LIGHT"

if [ "$NLP_PIPELINE" == "$NER" ]
  then
    SOURCE_PATH=$3
    DESTINATION_PATH=$4

    sh "$SPARK_HOME"/bin/spark-submit \
      --master local[*] \
      --class com.pipeline.nlp.ProcessDocuments \
      --conf "spark.jsl.settings.pretrained.cache_folder=/home/danilo/spark_nlp_models" \
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
        --conf "spark.jsl.settings.pretrained.cache_folder=/home/danilo/spark_nlp_models" \
        "$JAR_PATH" \
        "$NLP_PIPELINE"
fi

if [ "$NLP_PIPELINE" == "$LIGHT" ]
  then

    sh "$SPARK_HOME"/bin/spark-submit \
      --master local[*] \
      --class com.pipeline.nlp.ProcessDocuments \
      --conf "spark.driver.memory=12G" \
      --conf "spark.serializer=org.apache.spark.serializer.KryoSerializer" \
      --conf "spark.kryoserializer.buffer.max=2000M" \
      --conf "spark.driver.maxResultSize=0" \
      --conf "spark.hadoop.fs.s3a.access.key=ASIASRWSDKBGAZ7MIA6T" \
      --conf "spark.hadoop.fs.s3a.secret.key=sAoZrPdWPWBPbQzQ0D/crW8M1z1mvWH0bjsH3xMk" \
      --conf "spark.hadoop.fs.s3a.session.token=IQoJb3JpZ2luX2VjEK3//////////wEaCXVzLWVhc3QtMSJIMEYCIQDmH0bDtpS4Og4erHP6ujNyQJAbnqSDzjNvy7ye2T9USAIhAPHA2EtOczB3GS36iwPUKoGba0w8Xpeh7mevXZ5Krx1hKvgBCPb//////////wEQBBoMMTc1NDYwNTM2Mzk2Igws35XPnOKFDjPppLMqzAFsi/ip3EycqTi2mESJ4KVz8hhZcBDoztQfXhT/JI5loULYPKB362/vvv4xzoZlpN6h98KsRp2glWsnwHFoMti7Jk6ydA+EEfKVFfFQ5c1p+Dyho1PNRs8M3Hmca93GDUOzVXGlLrPSEn+nO0C42+kX62A/NmuIM4sQv2kDBUmXI4F9u9P/EA9f9nGVcG5GbqIBdaLl2UjbHBicS0BqHNSUy715GB8BDZ1DWGVjsfLcyv7wVXcbZKEXsVACkHjEnkjtZOWNL4kBkvF2fLIwvsnXnQY6lwEMxgfNzHEZxBLnUNKtLVwgS5dJuWHgXXA9en3arAhVTVFj/3r6OeMWPj1k7EjUlrsyizTtfeN6l67JODWimRW+mTRTvIa4AC01gNkkqBVIHBpd5EODGNzyyXe4/HsKt4Saegfq3QL+iWi1cyttt+s+BxbgxbcDLA3fiXNdQqSSwofIbREPBveJHSMrxsvY/Ow/2+mzgApv" \
      --conf "spark.hadoop.fs.s3a.aws.credentials.provider=org.apache.hadoop.fs.s3a.TemporaryAWSCredentialsProvider" \
      --conf "spark.hadoop.fs.s3a.impl=org.apache.hadoop.fs.s3a.S3AFileSystem" \
      --conf "spark.jars.packages=com.johnsnowlabs.nlp:spark-nlp_2.12:4.2.6,org.apache.hadoop:hadoop-aws:3.3.1,com.amazonaws:aws-java-sdk:1.11.901" \
      --conf "spark.hadoop.fs.s3a.path.style.access=true" \
      --conf "spark.jsl.settings.pretrained.cache_folder=s3://auxdata.johnsnowlabs.com/public/tmp/danilo/models/" \
      --conf "spark.jsl.settings.aws.region=us-east-1" \
      "$JAR_PATH" \
      "$NLP_PIPELINE"
fi