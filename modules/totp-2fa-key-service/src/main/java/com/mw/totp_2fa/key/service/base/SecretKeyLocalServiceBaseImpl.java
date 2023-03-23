/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.mw.totp_2fa.key.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.mw.totp_2fa.key.model.SecretKey;
import com.mw.totp_2fa.key.service.SecretKeyLocalService;
import com.mw.totp_2fa.key.service.persistence.SecretKeyPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the secret key local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.mw.totp_2fa.key.service.impl.SecretKeyLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.mw.totp_2fa.key.service.impl.SecretKeyLocalServiceImpl
 * @see com.mw.totp_2fa.key.service.SecretKeyLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class SecretKeyLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements SecretKeyLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.mw.totp_2fa.key.service.SecretKeyLocalServiceUtil} to access the secret key local service.
	 */

	/**
	 * Adds the secret key to the database. Also notifies the appropriate model listeners.
	 *
	 * @param secretKey the secret key
	 * @return the secret key that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SecretKey addSecretKey(SecretKey secretKey) {
		secretKey.setNew(true);

		return secretKeyPersistence.update(secretKey);
	}

	/**
	 * Creates a new secret key with the primary key. Does not add the secret key to the database.
	 *
	 * @param secretKeyId the primary key for the new secret key
	 * @return the new secret key
	 */
	@Override
	public SecretKey createSecretKey(long secretKeyId) {
		return secretKeyPersistence.create(secretKeyId);
	}

	/**
	 * Deletes the secret key with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param secretKeyId the primary key of the secret key
	 * @return the secret key that was removed
	 * @throws PortalException if a secret key with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SecretKey deleteSecretKey(long secretKeyId)
		throws PortalException {
		return secretKeyPersistence.remove(secretKeyId);
	}

	/**
	 * Deletes the secret key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param secretKey the secret key
	 * @return the secret key that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SecretKey deleteSecretKey(SecretKey secretKey) {
		return secretKeyPersistence.remove(secretKey);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(SecretKey.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return secretKeyPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.mw.totp_2fa.key.model.impl.SecretKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return secretKeyPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.mw.totp_2fa.key.model.impl.SecretKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return secretKeyPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return secretKeyPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return secretKeyPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public SecretKey fetchSecretKey(long secretKeyId) {
		return secretKeyPersistence.fetchByPrimaryKey(secretKeyId);
	}

	/**
	 * Returns the secret key with the matching UUID and company.
	 *
	 * @param uuid the secret key's UUID
	 * @param companyId the primary key of the company
	 * @return the matching secret key, or <code>null</code> if a matching secret key could not be found
	 */
	@Override
	public SecretKey fetchSecretKeyByUuidAndCompanyId(String uuid,
		long companyId) {
		return secretKeyPersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the secret key with the primary key.
	 *
	 * @param secretKeyId the primary key of the secret key
	 * @return the secret key
	 * @throws PortalException if a secret key with the primary key could not be found
	 */
	@Override
	public SecretKey getSecretKey(long secretKeyId) throws PortalException {
		return secretKeyPersistence.findByPrimaryKey(secretKeyId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(secretKeyLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SecretKey.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("secretKeyId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(secretKeyLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(SecretKey.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("secretKeyId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(secretKeyLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SecretKey.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("secretKeyId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return secretKeyLocalService.deleteSecretKey((SecretKey)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return secretKeyPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the secret key with the matching UUID and company.
	 *
	 * @param uuid the secret key's UUID
	 * @param companyId the primary key of the company
	 * @return the matching secret key
	 * @throws PortalException if a matching secret key could not be found
	 */
	@Override
	public SecretKey getSecretKeyByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {
		return secretKeyPersistence.findByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns a range of all the secret keies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.mw.totp_2fa.key.model.impl.SecretKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of secret keies
	 * @param end the upper bound of the range of secret keies (not inclusive)
	 * @return the range of secret keies
	 */
	@Override
	public List<SecretKey> getSecretKeies(int start, int end) {
		return secretKeyPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of secret keies.
	 *
	 * @return the number of secret keies
	 */
	@Override
	public int getSecretKeiesCount() {
		return secretKeyPersistence.countAll();
	}

	/**
	 * Updates the secret key in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param secretKey the secret key
	 * @return the secret key that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SecretKey updateSecretKey(SecretKey secretKey) {
		return secretKeyPersistence.update(secretKey);
	}

	/**
	 * Returns the secret key local service.
	 *
	 * @return the secret key local service
	 */
	public SecretKeyLocalService getSecretKeyLocalService() {
		return secretKeyLocalService;
	}

	/**
	 * Sets the secret key local service.
	 *
	 * @param secretKeyLocalService the secret key local service
	 */
	public void setSecretKeyLocalService(
		SecretKeyLocalService secretKeyLocalService) {
		this.secretKeyLocalService = secretKeyLocalService;
	}

	/**
	 * Returns the secret key persistence.
	 *
	 * @return the secret key persistence
	 */
	public SecretKeyPersistence getSecretKeyPersistence() {
		return secretKeyPersistence;
	}

	/**
	 * Sets the secret key persistence.
	 *
	 * @param secretKeyPersistence the secret key persistence
	 */
	public void setSecretKeyPersistence(
		SecretKeyPersistence secretKeyPersistence) {
		this.secretKeyPersistence = secretKeyPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.mw.totp_2fa.key.model.SecretKey",
			secretKeyLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.mw.totp_2fa.key.model.SecretKey");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SecretKeyLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return SecretKey.class;
	}

	protected String getModelClassName() {
		return SecretKey.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = secretKeyPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = SecretKeyLocalService.class)
	protected SecretKeyLocalService secretKeyLocalService;
	@BeanReference(type = SecretKeyPersistence.class)
	protected SecretKeyPersistence secretKeyPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}