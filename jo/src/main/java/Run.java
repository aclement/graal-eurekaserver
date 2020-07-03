import java.util.Iterator;
import java.util.ServiceLoader;

import javax.ws.rs.ext.MessageBodyReader;

public class Run {

    public static void main(String[] argv) {
        ServiceLoader<MessageBodyReader> sl = ServiceLoader.load(MessageBodyReader.class);
        Iterator<MessageBodyReader> it = sl.iterator();
        int loadedOK = 0;
        int attemptedLoad = 0;
        int loadFailedMissingCtor = 0;
        while (it.hasNext()) {
            attemptedLoad++;
            try {
                it.next();
                loadedOK++;
            } catch (Throwable e) {
                System.out.println("Problem " + e.toString());
                if (e.getCause().getCause().toString().contains("NoSuchMethodException")) {
                    loadFailedMissingCtor++;
                    System.out.println(e.getCause().getCause());
                }
            }
        }
        System.out.println("Attempted to load " + attemptedLoad + " entries: " + loadedOK + " loaded OK, "
                + loadFailedMissingCtor + " failed missing nullary ctor");
    }
}