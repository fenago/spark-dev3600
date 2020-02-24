CREATE EXTERNAL TABLE maint_table

(resourceid STRING, eventDate STRING,
technician STRING, description STRING)

ROW FORMAT DELIMITED FIELDS TERMINATED BY ","


STORED AS TEXTFILE LOCATION "/home/jovyan/work/spark-dev3600/sensormaint.csv";
