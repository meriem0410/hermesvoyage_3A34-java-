package edu.esprit.GestionTransport.Entity;



import java.util.HashMap;
import java.util.UUID;

public class SessionManager {
    private static final HashMap<String, Transport> sessionStore = new HashMap<>();
    private static String currentSessionId;

    /**
     * Crée une nouvelle session pour le transport donné et retourne l'ID de session.
     *
     * @param transport Le transport pour créer une session.
     * @return L'ID de session pour la nouvelle session créée.
     */
    public static String createSession(Transport transport) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, transport);
        currentSessionId = sessionId;
        return sessionId;
    }

    /**
     * Récupère l'objet transport associé à l'ID de session donné.
     *
     * @param sessionId L'ID de session.
     * @return L'objet transport associé à l'ID de session, ou null si la session n'existe pas.
     */
    public static Transport getTransportFromSession(String sessionId) {
        return sessionStore.get(sessionId);
    }

    /**
     * Termine la session spécifiée en supprimant l'entrée correspondante dans la HashMap.
     *
     * @param sessionId L'ID de session à terminer.
     */
    public static void terminateSession(String sessionId) {
        sessionStore.remove(sessionId);
    }

    /**
     * Récupère l'ID de session actuel.
     *
     * @return L'ID de session actuel, ou null s'il n'y a pas de session active.
     */
    public static String getCurrentSessionId() {
        return currentSessionId;
    }
}
