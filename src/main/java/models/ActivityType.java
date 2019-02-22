package models;

/**
 * A enum containing values indicating a type of an activity.
 */
public enum ActivityType {
    /**
     * Activity type indicating OS-wide activity.
     */
    OS,

    /**
     * Activity type indicating a change in an existing line of code.
     */
    LINES_CHANGE,

    /**
     * An activity type indicating a deletion of a line of code.
     */
    LINES_DELETE,

    /**
     * An activity type indicating an insertion of a line of code.
     */
    LINES_INSERT,

    /**
     * An activity type indicating an insertion of a comment block or line.
     */
    COMMENTS_ADDED,

    /**
     * An activity type indicating a deletion of a comment block or line.
     */
    COMMENTS_DELETED,

    /**
     * An activity type indicating an insertion of a test case.
     */
    TESTS_ADDED,

    /**
     * An activity type indicating a deletion of a test case.
     */
    TESTS_DELETED,

    /**
     * An activity type indicating an opening of a new file in a code editor.
     */
    TAB_NAME,

    /**
     * An activity type indicating a command execution in an editor's command line interface.
     */
    EXECUTED_COMMAND
}
