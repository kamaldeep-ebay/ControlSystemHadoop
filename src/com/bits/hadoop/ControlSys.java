package com.bits.hadoop;

 	
 	import java.io.IOException;
 	import java.util.*;
 	
 	import org.apache.hadoop.fs.Path;
 	
 	import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
 	
 	public class ControlSys {
 	
 	   public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
 	      		  
 	     public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
 	      
 	       
 	       /*StringTokenizer tokenizer = new StringTokenizer(line);
 	       
 	       while (tokenizer.hasMoreTokens()) {
 	       
 	    	 word.set(tokenizer.nextToken());
 	         output.collect(word, one);
 	       }*/
 	    	 String line = value.toString();
	    	 String[] array = line.split(",");
	    	// first comes IP then Memory then hard disk space
	    	 double memlimit = Double.parseDouble(array[1]);
	    	 double hddlimit = Double.parseDouble(array[2]);
             
	    	 Limits limits = new Limits();
	    	 

             if(hddlimit < limits.getHddLimit())
             {
                 output.collect(new Text("HDD Limit Exceeded in"),new Text(array[0]));
                 //System.out.print("ip"+array[0]);
             }
          
	    	 if(memlimit < limits.getMemLimit())
	    	     {
	    	         output.collect(new Text("Memory Limit Exceeded in"),new Text(array[0]));
	    	         //System.out.print("ip"+array[0]);
	    	     }
	    	 
	      }
 	   }
 	
 	   public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
 	     public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
 	       //int sum = 0;
 	       
 	       StringBuilder ips = new StringBuilder();
 	       while (values.hasNext()) {
 	           
 	       // System.out.print("values"+values.next().toString());
 	    	   ips.append(values.next().toString()).append(",");
 	       }
 	       output.collect(key,new Text(ips.toString()));
 	     }
 	   }
 	
 	   public static void main(String[] args) throws Exception {
 	
 		 //String key = args[2];  
 		 		 
 		 JobConf conf = new JobConf(ControlSys.class);
 	     conf.setJobName("control system");
 	     
 	     //conf.set("key", key);
 	     
 	     conf.setOutputKeyClass(Text.class);
 	     conf.setOutputValueClass(Text.class);
 	
 	     conf.setMapperClass(Map.class);
 	     //conf.setCombinerClass(Reduce.class);
 	     conf.setReducerClass(Reduce.class);
 	
 	     conf.setInputFormat(TextInputFormat.class);
 	     conf.setOutputFormat(TextOutputFormat.class);
 	
 	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
 	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
 	
 	     JobClient.runJob(conf);
 	   }
 	}