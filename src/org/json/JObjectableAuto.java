package org.json;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.time.LocalDateTime;

public class JObjectableAuto implements JObjectable
{
    static LocalDateTime ConvertStringToLocalDateTime(String value)
    {
        return LocalDateTime.parse(value);
    }

    static Object ConvertStringToPrimativeObject(Class<?> type, String data)
    {
        if (type == int.class)
        {
            return Integer.parseInt(data);
        }

        if (type == float.class)
        {
            return Float.parseFloat(data);
        }

        if (type == double.class)
        {
            return Double.parseDouble(data);
        }

        if (type == boolean.class)
        {
            return Boolean.parseBoolean(data);
        }

        if (type == char.class)
        {
            return data.charAt(0);
        }

        if (type == String.class)
        {
            return data;
        }

        System.err.println("Unhandled primative type: '" + type + "'");

        return null;
    }

    protected JSONObject toJsonAuto()
    {
        System.out.println("--- TO JSON AUTO ---");
        JSONObject obj = new JSONObject();
    
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                Class<?> type = field.getType();

                field.setAccessible(true);
                Object value = field.get(this);

                if (value == null)
                {
                    System.out.println("Skipped " + field.getName() + " as it was null");
                    continue;
                }

                boolean isJObjectable = false;
                boolean isArray = type.isArray();

                Class<?>[] interfaces = !isArray ? type.getInterfaces() : type.getComponentType().getInterfaces();
                for (Class<?> i : interfaces)
                {
                    if (i == JObjectable.class)
                    {
                        isJObjectable = true;
                    }
                }

                if (!isArray)
                {
                    if (isJObjectable)
                    {
                        JObjectable jobjectable = (JObjectable)value;
                        obj.put(field.getName(), jobjectable.toJson());
                    }
                    else
                    {
                        obj.put(field.getName(), value);
                    }
                }
                else
                {
                    if (isJObjectable)
                    {
                        JObjectable[] jobjs = (JObjectable[])value;
                        
                        System.out.println("Jobj array for " + field.getName());
                        System.out.println(jobjs.length + " jobjs in array");
                        System.out.println("Length check: " + ((Object[])value).length);

                        for (JObjectable jobj : jobjs)
                        {
                            obj.append(field.getName(), jobj.toJson());
                        }
                    }
                    else
                    {
                        Object[] objects = new Object[Array.getLength(value)];
                        for (int i = 0; i < objects.length; i++)
                        {
                            objects[i] = Array.get(value, i);
                        }

                        for (Object v : objects)
                        {
                            obj.append(field.getName(), v);
                        }
                    }
                }
            }
            catch (IllegalAccessException ex)
            {
                System.err.println("Access error on field: " + field.getName());
                ex.printStackTrace();
            }
        }

        System.out.println("--- TO JSON AUTO ENDDDDD ---");
        return obj;
    }

    protected void fromJsonAuto(JSONObject obj)
    {
        System.out.println("--- FROM JSON AUTO ---");

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields)
        {
            try
            {
                Class<?> type = field.getType();
                Object fieldValue = field.get(this);

                field.setAccessible(true);

                boolean isJObjectable = false;
                boolean isArray = type.isArray();

                Class<?>[] interfaces = !isArray ? type.getInterfaces() : type.getComponentType().getInterfaces();
                for (Class<?> i : interfaces)
                {
                    if (i == JObjectable.class)
                    {
                        isJObjectable = true;
                    }
                }

                if (!isArray)
                {
                    if (isJObjectable)
                    {
                        JSONObject jobj = obj.getJSONObject(field.getName());
                        System.out.println("Trying instancing of " + type + "...");
                        try
                        {
                            type.getConstructor().setAccessible(true);
                            Object instance = type.getConstructor().newInstance();
                            JObjectable jObjectable = (JObjectable)instance;
                            System.out.println("jobjectable?=" + (jObjectable == null) + ", " + "jobj?=" + (jobj == null));
                            jObjectable.fromJson(jobj);

                            field.set(this, jObjectable);
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Jobject single diff");
                            ex.printStackTrace();
                        }
                    }
                    else if (type.equals(LocalDateTime.class))
                    {
                        field.set(this, ConvertStringToLocalDateTime(obj.optString(field.getName())));
                    }
                    else if (type.isEnum())
                    {
                        @SuppressWarnings("unchecked")
                        Enum<?> enumValue = Enum.valueOf((Class<Enum>)type, obj.optString(field.getName()));
                        field.set(this, enumValue);
                    }
                    else
                    {
                        Object data = ConvertStringToPrimativeObject(type, obj.optString(field.getName()));
                        field.set(this, data);
                    }
                }
                else
                {
                    if (isJObjectable)
                    {
                        JSONArray jArray = obj.getJSONArray(field.getName());
                        Object arrayInstance = Array.newInstance(type.getComponentType(), jArray.length());

                        for (int i = 0; i < jArray.length(); i++)
                        {
                            try
                            {
                                Object elementInstance = type.getComponentType().getDeclaredConstructor().newInstance();
                                JObjectable jobjInstance = (JObjectable)elementInstance;

                                jobjInstance.fromJson(jArray.getJSONObject(i));

                                Array.set(arrayInstance, i, jobjInstance);
                            }
                            catch (Exception ex)
                            {
                                System.out.println("Jobj array diff");
                                ex.printStackTrace();
                            }
                        }

                        field.set(this, arrayInstance);

                        //JObjectable[] jobjs = (JObjectable[])value;
                        
                        //System.out.println("Jobj array for " + field.getName());
                        //System.out.println(jobjs.length + " jobjs in array");
                        //System.out.println("Length check: " + ((Object[])value).length);

                        //for (JObjectable jobj : jobjs)
                        //{
                            //obj.append(field.getName(), jobj.toJson());
                        //}
                    }
                    else
                    {
                        JSONArray jArray = obj.getJSONArray(field.getName());
                        Object arrayInstance = Array.newInstance(type.getComponentType(), jArray.length());

                        Object[] objects = new Object[jArray.length()];

                        for (int i = 0; i < jArray.length(); i++)
                        {
                            if (type.getComponentType().equals(LocalDateTime.class))
                            {
                                Array.set(objects, i, ConvertStringToLocalDateTime(jArray.optString(i)));
                            }
                            else if (type.getComponentType().isEnum())
                            {
                                @SuppressWarnings("unchecked")
                                Enum<?> enumValue = Enum.valueOf((Class<Enum>)type.getComponentType(), jArray.optString(i));
                                Array.set(arrayInstance, i, enumValue);
                            }
                            else
                            {
                                Object data = ConvertStringToPrimativeObject(type.getComponentType(), jArray.optString(i));
                                Array.set(arrayInstance, i, data);
                            }
                        }
                        
                        field.set(this, arrayInstance);
                    }
                }
            }
            catch (IllegalAccessException ex)
            {
                System.err.println("Access error on field: " + field.getName());
                ex.printStackTrace();
            }
        }

        System.out.println("--- FROM JSON AUTO ENDDDDD ---");
    }

    @Override
    public JSONObject toJson()
    {
        return toJsonAuto();
    }

    @Override
    public void fromJson(JSONObject obj)
    {
        fromJsonAuto(obj);
    }
}
