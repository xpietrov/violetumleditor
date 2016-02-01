package com.horstmann.violet.product.diagram.usecase;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The bean info for the ClassNode type.
 */
public class UseCaseNodeBeanInfo extends SimpleBeanInfo
{
    @Override
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        try
        {
            PropertyDescriptor titleDescriptor = new PropertyDescriptor("title", UseCaseNode.class);
            titleDescriptor.setValue("priority", new Integer(1));
            PropertyDescriptor descriptionDescriptor = new PropertyDescriptor("description", UseCaseNode.class);
            descriptionDescriptor.setValue("priority", new Integer(2));
            return new PropertyDescriptor[]
                    {
                            titleDescriptor,
                            descriptionDescriptor
                    };
        }
        catch (IntrospectionException exception)
        {
            return null;

        }
    }
}
