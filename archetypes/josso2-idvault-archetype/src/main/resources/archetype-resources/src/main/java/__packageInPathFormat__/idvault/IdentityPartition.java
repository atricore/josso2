package ${package}.idvault;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atricore.idbus.kernel.common.support.services.IdentityServiceLifecycle;
import org.atricore.idbus.kernel.main.provisioning.domain.Group;
import org.atricore.idbus.kernel.main.provisioning.domain.User;
import org.atricore.idbus.kernel.main.provisioning.exception.ProvisioningException;
import org.atricore.idbus.kernel.main.provisioning.impl.AbstractIdentityPartition;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;

/**
 * Identity Partition implementation.
 */
public class IdentityPartition extends AbstractIdentityPartition
        implements InitializingBean,
        DisposableBean,
        IdentityServiceLifecycle {

    private static final Log logger = LogFactory.getLog(IdentityPartition.class);

    @Override
    public void boot() throws Exception {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public Group findGroupById(String s) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Group findGroupByName(String s) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Collection<Group> findGroupsByUserName(String s) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Collection<Group> findAllGroups() throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Group addGroup(Group group) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Group updateGroup(Group group) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public void deleteGroup(String s) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public User addUser(User user) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public void deleteUser(String s) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public User findUserById(String s) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public User findUserByUserName(String s) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Collection<User> findAllUsers() throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public User updateUser(User user) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }

    @Override
    public Collection<User> getUsersByGroup(Group group) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported by this implementation");
    }
}
