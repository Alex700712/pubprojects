package Database.Service;

import Database.DAO.OrganizationDAO;
import Database.Entities.Key;
import Database.Entities.Organization;

import java.util.List;

public class OrganizationService implements Service<Organization> {

    public OrganizationDAO orgDAO = new OrganizationDAO();

    @Override
    public void addEntity(Organization entity) { orgDAO.add(entity); }

    @Override
    public void updateEntity(Organization entity) { orgDAO.update(entity); }

    @Override
    public void deleteEntity(Organization entity) { orgDAO.delete(entity); }

    @Override
    public Organization findEntityById(int id) { return orgDAO.findEntityById(id); }

    @Override
    public List<Organization> findAllEntities() { return orgDAO.findAllEntities(); }

}
