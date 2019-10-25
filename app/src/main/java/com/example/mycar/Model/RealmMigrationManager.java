package com.example.mycar.Model;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class RealmMigrationManager implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        for(long versao = oldVersion; versao < newVersion; versao++){
            Log.d("MIGRACOES", "Migrando da "+oldVersion+" para "+newVersion);
            passoDeMigracao(realm, versao, versao + 1);
        }
    }

    private void passoDeMigracao(DynamicRealm realm, long versaoVelha, long versaoNova){
        if(versaoVelha == 0 && versaoNova == 1){
            RealmSchema schema = realm.getSchema();
            RealmObjectSchema compromissoSchema = schema.get("Compromisso");
            compromissoSchema.addField( "caminhoFotografia", String.class );
        }
    }

}