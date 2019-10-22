package com.example.mycar.Model;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class FuelDao {
    private ArrayList<Fuel> dataBase;

    public ArrayList<Fuel> getList(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults lista = realm.where(Fuel.class).findAll().sort("currentKm", Sort.DESCENDING);
        dataBase.clear();
        dataBase.addAll(realm.copyFromRealm(lista));
        return dataBase;
    }

    public void addItem(Fuel f){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(f);
        realm.commitTransaction();
    }

    public int updateItem(Fuel f){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(f);
        realm.commitTransaction();

        for(int i = 0; i < dataBase.size(); i++){
            if(dataBase.get(i).getId().equals(f.getId())){
                dataBase.set(i, f);
                return i;
            }
        }
        return -1;
    }

    public int deleteItem(Fuel f){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Fuel.class).equalTo("id", f.getId()).findFirst().deleteFromRealm();
        realm.commitTransaction();

        for(int i = 0; i < dataBase.size(); i++){
            if(dataBase.get(i).getId().equals(f.getId())){
                dataBase.remove(i);
                return i;
            }
        }
        return -1;
    }

    public Fuel getObjectById(String id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Fuel f = realm.copyFromRealm(realm.where(Fuel.class).equalTo("id", id).findFirst());
        realm.commitTransaction();
        return f;
    }


    private static FuelDao INSTANCIA;

    public static FuelDao getInstance(){
        if (INSTANCIA == null){
            INSTANCIA = new FuelDao();
        }
        return INSTANCIA;
    }

    private FuelDao(){
        dataBase = new ArrayList<Fuel>();
    }
}
