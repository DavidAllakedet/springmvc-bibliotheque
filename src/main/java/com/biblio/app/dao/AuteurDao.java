package com.biblio.app.dao;

import com.biblio.app.entities.AuteurEntity;

public class AuteurDao extends RepositoryImpl<AuteurEntity> implements IAuteurDao {
    public AuteurDao() { super(AuteurEntity.class); }
}
