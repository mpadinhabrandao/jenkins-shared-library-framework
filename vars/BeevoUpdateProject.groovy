def call(
    List<String> actions
) {
    String cmd_arguments = "" 
    actions.each {
        if (!it.startsWith("--")) it = "--" + it
        cmd_arguments = cmd_arguments + " " + it
    }
    sh  """
        beevo update-project ${cmd_arguments}
    """
}