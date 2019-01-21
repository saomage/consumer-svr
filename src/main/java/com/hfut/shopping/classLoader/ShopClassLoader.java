package com.hfut.shopping.classLoader;

public class ShopClassLoader extends ClassLoader {
	
	private byte[] classBytes;
	
	public ShopClassLoader(byte[] classBytes) {
		super();
		this.classBytes = classBytes;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if (null == classBytes || classBytes.length == 0)
            throw new ClassNotFoundException("load the class " + name + " failed");

        return this.defineClass(name, classBytes, 0, classBytes.length);
	}
	
	@Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class<?> clazz = null;

        if (!"com.hfut.shopping.pay.PayImpl".equals(name)) {
            try {
                ClassLoader system = ClassLoader.getSystemClassLoader();
                clazz = system.loadClass(name);
                if (clazz != null) {
                    if (resolve)
                        resolveClass(clazz);
                    return clazz;
                }
            } catch (Exception e) {
                //ignore
            }
        }


        try {
            clazz = findClass(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (clazz == null && getParent() != null) {
            getParent().loadClass(name);
        }

        return clazz;
    }
}
