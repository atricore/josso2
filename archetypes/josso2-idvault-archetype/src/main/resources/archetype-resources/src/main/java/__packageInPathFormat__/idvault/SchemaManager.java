package ${package}.idvault;

import org.atricore.idbus.kernel.main.provisioning.domain.GroupAttributeDefinition;
import org.atricore.idbus.kernel.main.provisioning.domain.UserAttributeDefinition;
import org.atricore.idbus.kernel.main.provisioning.exception.ProvisioningException;
import org.atricore.idbus.kernel.main.provisioning.impl.AbstractSchemaManager;

import java.util.Collection;

/**
 * Schema Manager implementation.
 */
public class SchemaManager extends AbstractSchemaManager {

    public void init() {
    }

    @Override
    public UserAttributeDefinition addUserAttribute(UserAttributeDefinition userAttributeDefinition) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public UserAttributeDefinition updateUserAttribute(UserAttributeDefinition userAttributeDefinition) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public void deleteUserAttribute(String attributeId) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public UserAttributeDefinition findUserAttributeById(String attributeId) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public UserAttributeDefinition findUserAttributeByName(String name) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Collection<UserAttributeDefinition> listUserAttributes() throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public GroupAttributeDefinition addGroupAttribute(GroupAttributeDefinition groupAttributeDefinition) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public GroupAttributeDefinition updateGroupAttribute(GroupAttributeDefinition groupAttributeDefinition) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public void deleteGroupAttribute(String attributeId) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public GroupAttributeDefinition findGroupAttributeById(String attributeId) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public GroupAttributeDefinition findGroupAttributeByName(String name) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Collection<GroupAttributeDefinition> listGroupAttributes() throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }
}
