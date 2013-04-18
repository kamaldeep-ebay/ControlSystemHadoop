package com.bits.hadoop;

public class Limits
{
    public final Double memlimit;
     public final Double  hddlimit;
     
     public Limits()
     {
         memlimit = 3.0;
         hddlimit = 5.0;
     }
     
     public  Double getMemLimit()
     {
         return memlimit;
     }
     public  Double getHddLimit()
     {
         return hddlimit;
     }
}
