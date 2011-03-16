/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.demos.jpa.archive;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceProperty;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @author Scott Marlow
 */
@Stateful
public class SimpleStatefulSessionBean implements SimpleStatefulSessionLocal {
    private String state;

    @PersistenceContext(type= PersistenceContextType.TRANSACTION,unitName = "H2DS",
        properties=@PersistenceProperty(name="hibernate.hbm2ddl.auto", value="create-drop"))
    private EntityManager entityManager;

    public String echo(String msg) {
        System.out.println("Called echo on " + this);
        System.out.println("call the entity manager");

        SimpleEntity entity = new SimpleEntity();
        entity.setId(1);
        entity.setName("Douglas Adams");
        entityManager.persist(entity);
        System.out.println("saved new Entity for " + entity.getName());
        entity = entityManager.find(SimpleEntity.class, new Integer(1));
        System.out.println("read back Entity for " + entity.getName());

        return "Echo " + msg + ":" + state + " entitymanager find should return null, it returned = " +entity;
    }

    public void setState(String s) {
        System.out.println("Called setState on " + this);
        this.state = s;
    }

    @Override
    public String getState() {
        return this.state;
    }
}