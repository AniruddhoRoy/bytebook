package com.example.project_7;

import java.io.*;
import java.util.ArrayList;

class Item implements Serializable {
    String name;
    String path;
    Item(String name,String path){
        this.name = name;
        this.path = path;
    }
    @Override
    public String toString() {
        return name;
    }
}
public class RecentHandler {
    private ArrayList<Item> files;
    private File file = new File("Recent.logs");

    RecentHandler(){
        if(file.exists()) {
            try {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(file));
                files = (ArrayList<Item>) os.readObject();
            } catch (Exception e) {
                System.out.println(e);
                files = new ArrayList<>();
                System.out.println("Error while reading recent files");
            }
        }
        else{
            files = new ArrayList<>();
            save();
        }
    }
    private void save(){
        try{
            ObjectOutputStream obs = new ObjectOutputStream(new FileOutputStream(file));
            obs.writeObject(files);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Error while writing recent files");
        }
    }
    void add_recent_file(Item item){
        files.add(item);
        save();
    }
    ArrayList<Item> get_recent_files(){
        return files;
    }
    void remove_file(int idnex){
        files.remove(idnex);
        save();
    }
    boolean isexist(String name,String path){
        for(Item item:files){
            if(item.path.equals(path)){
               return true;
            }
        }
        files.add(new Item(name,path));
        save();
        return false;
    }
}
