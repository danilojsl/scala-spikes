Create an assembly sbt task on IntelliJ or run sbt assembly command. 
```
sbt assembly
```

Running SparkNLP Annotators with spark-submit:

Run NER Pipeline
```
./run_nlp_pipeline.sh ~/jars/scala-spikes-assembly-0.1.jar NER ~/datasets/test_docs.csv ~/ner
```

Run BERT Embeddings Pipeline:
```
./run_nlp_pipeline.sh ~/jars/scala-spikes-assembly-0.1.jar BERT ~/models/tmp_bert_base_cased_pt
```

Running on GCP

Clusters are located in:
BIGDATA --> Dataptoc --> Clusters

Buckets are located in:
Cloud Storage --> Buckets

Check HDFS directories
Connect to master node through SSH 
```
hdfs dfs -ls /
hdfs -mkdir /tmp/danilo
hdfs dfs -rm /tmp/danilo/scala-spikes-assembly-0.1.jar
hdfs dfs -cp gs://test-bucket-danilo/jars/scala-spikes-assembly-0.1.jar /tmp/danilo
```

To get HDFS node address:
Configuration: hdfs:dfs.namenode.servicerpc-address

Cloud Shell
```
gcloud dataproc jobs submit spark --cluster=cluster-test --region=us-central1 --jar=hdfs://cluster-test-m:8051/tmp/danilo/scala-spikes-assembly-0.1.jar -- gs://test-bucket-danilo/datasets/docs.csv hdfs://cluster-test-m:8051/tmp/danilo/ner
```

```
gcloud dataproc jobs submit spark --cluster=cluster-test --region=us-central1 --jar=hdfs://cluster-test-m:8051/tmp/danilo/scala-spikes-assembly-0.1.jar -- CONTEXT-SPELL
```

Copy from HDFS to local file system
```
hdfs dfs -copyToLocal /tmp/danilo/ner/part-00000-1f7707d1-7b8b-45b4-bd13-cbf0e34ac8a5-c000.json .
```
