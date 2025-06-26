package utilities;

public class PageContextManager {

    private static String currentPageName;

    public static void setCurrentPage(String pageName) {
        currentPageName = pageName;
    }

    public static String getCurrentPage() {
        if (currentPageName == null || currentPageName.isEmpty()) {
            throw new RuntimeException("Current page context is not set! Please verify page first.");
        }
        return currentPageName;
    }
}
