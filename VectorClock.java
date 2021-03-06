package rcoem.ds.lamport;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class VectorClock {
    public static Integer getKey(Set<Map.Entry<Integer,Integer>> entrySet,Integer value){
        Integer key=null;
        for(Map.Entry entry:entrySet){
            if(value.equals(entry.getValue())){
                key=(Integer)entry.getKey();
                break;
            }
        }
        return key;
    }
    public static void display(int vector[][][]){
        System.out.println("\n Vector Time stamps are\n");
    	for(int i=0;i<vector.length;i++){
            for(int j=0;j<vector[i].length;j++){
                System.out.print("[");
                for(int k=0;k<vector[i][j].length;k++)
                    System.out.print(vector[i][j][k]+" ");
                System.out.print("] ");
            }
            System.out.println();
        }
    }
    public static void getVectorTimestamp(){
        Scanner in=new Scanner(System.in);
        System.out.println("Enter no. of processes:");
        int noOfProcesses=in.nextInt();
        int noOfEvents[]=new int[noOfProcesses];
        int counter[][]=new int[noOfProcesses][noOfProcesses];
        
        System.out.println("Enter no. of events per process:");
        
        for(int i=0;i<noOfProcesses;i++)
            noOfEvents[i]=in.nextInt();
        
        System.out.println("Enter no. of relationships:");
        int noOfRelationships=in.nextInt();
        Map<Integer,Integer> map=new HashMap<>();
        
        System.out.println("Enter happened-before relationships:");
        for(int i=0;i<noOfRelationships;i++){
            String input=in.next();
            map.put(Integer.parseInt(input.split("-")[0]),Integer.parseInt(input.split("-")[1]));
        }
        
        ArrayList<Integer> keys=new ArrayList<>(map.keySet()),values=new ArrayList<>(map.values());
        Collections.sort(values,new Comparator<Integer>(){
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1%10,o2%10);
            }
        });
        Collections.sort(keys,new Comparator<Integer>(){
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1%10,o2%10);
            }
        });
        
        int vectors[][][]=new int[noOfProcesses][][];
        for(int i=0;i<noOfProcesses;i++){
            vectors[i]=new int[noOfEvents[i]][noOfProcesses];
            for(int j=0;j<noOfEvents[i];j++){
                vectors[i][j][i]=j+1;
            }
        }
        for(Integer value:values){
            Integer key=getKey(map.entrySet(),value);
            if(key%10-1>0){
                for(int i=0;i<noOfProcesses;i++){
                    if(i==key/10)
                        vectors[key/10][key%10][i]=Integer.max(vectors[key/10][key%10][i],vectors[key/10][key%10-1][i]+1);
                    else
                        vectors[key/10][key%10][i]=vectors[key/10][key%10-1][i];
                }
                for(int i=0;i<noOfProcesses;i++){
                    vectors[key/10][key%10][i]=Integer.max(vectors[key/10][key%10][i],vectors[key/10][key%10-1][i]);
                    if(key%10+1<noOfEvents[key/10])
                        vectors[key/10][key%10+1][i]=Integer.max(vectors[key/10][key%10+1][i],vectors[key/10][key%10][i]);
                }
            }else{
                for(int i=0;i<noOfProcesses;i++){
                    if(i==key/10)
                        vectors[key/10][key%10][i]=Integer.max(vectors[key/10][key%10][i],key%10+1);
                    if(key%10+1<noOfEvents[key/10])
                        vectors[key/10][key%10+1][i]=Integer.max(vectors[key/10][key%10+1][i],vectors[key/10][key%10][i]);
                }
            }
            if(value%10-1>0){
                for(int i=0;i<noOfProcesses;i++){
                    if(i==value/10)
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10-1][i]+1,vectors[key/10][key%10][i]+1);
                    else
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],vectors[key/10][key%10][i]);
                }
                for(int i=0;i<noOfProcesses;i++){
                    vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],vectors[value/10][value%10-1][i]);
                    if(value%10+1<noOfEvents[value/10])
                        vectors[value/10][value%10+1][i]=Integer.max(vectors[value/10][value%10+1][i],vectors[value/10][value%10][i]);
                }
            }else{
                for(int i=0;i<noOfProcesses;i++){
                    if(i==value/10)
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],value%10+1);
                    else
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],vectors[key/10][key%10][i]);
                    if(value%10+1<noOfEvents[value/10])
                        vectors[value/10][value%10+1][i]=Integer.max(vectors[value/10][value%10+1][i],vectors[value/10][value%10][i]);
                }
            }
        }
        for(Integer key:keys){
            Integer value=map.get(key);
            if(key%10-1>0){
                for(int i=0;i<noOfProcesses;i++){
                    if(i==key/10)
                        vectors[key/10][key%10][i]=Integer.max(vectors[key/10][key%10][i],vectors[key/10][key%10-1][i]+1);
                    else{
                        vectors[key/10][key%10][i]=vectors[key/10][key%10-1][i];
                    }
                }
                for(int i=0;i<noOfProcesses;i++){
                    vectors[key/10][key%10][i]=Integer.max(vectors[key/10][key%10][i],vectors[key/10][key%10-1][i]);
                    if(key%10+1<noOfEvents[key/10])
                        vectors[key/10][key%10+1][i]=Integer.max(vectors[key/10][key%10+1][i],vectors[key/10][key%10][i]);
                }
            }else{
                for(int i=0;i<noOfProcesses;i++){
                    if(i==key/10)
                        vectors[key/10][key%10][i]=Integer.max(vectors[key/10][key%10][i],key%10+1);
                    if(key%10+1<noOfEvents[key/10])
                        vectors[key/10][key%10+1][i]=Integer.max(vectors[key/10][key%10+1][i],vectors[key/10][key%10][i]);
                }
            }
            if(value%10-1>0){
                for(int i=0;i<noOfProcesses;i++){
                    if(i==value/10)
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10-1][i]+1,vectors[key/10][key%10][i]+1);
                    else
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],vectors[key/10][key%10][i]);
                }
                for(int i=0;i<noOfProcesses;i++){
                    vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],vectors[value/10][value%10-1][i]);
                    if(value%10+1<noOfEvents[value/10])
                        vectors[value/10][value%10+1][i]=Integer.max(vectors[value/10][value%10+1][i],vectors[value/10][value%10][i]);
                }
            }else{
                for(int i=0;i<noOfProcesses;i++){
                    if(i==value/10)
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],value%10+1);
                    else
                        vectors[value/10][value%10][i]=Integer.max(vectors[value/10][value%10][i],vectors[key/10][key%10][i]);
                    if(value%10+1<noOfEvents[value/10])
                        vectors[value/10][value%10+1][i]=Integer.max(vectors[value/10][value%10+1][i],vectors[value/10][value%10][i]);
                }
            }
        }
        display(vectors);
    }
    public static void main(String[] args) {
        getVectorTimestamp();
    }
}
