Steps to run a spark application:

Step 1: Compile the project into a JAR file
Step 2: scp auctionsapp-1.0.jar user01@ipaddress:/home/jovyan/work/spark-dev3600/.
Step 3: spark-submit --class solutions.AuctionsApp --master local[2] auctionsapp-1.0.jar
