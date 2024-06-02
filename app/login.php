<?php
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
    require_once("/homepages/33/d1004788311/htdocs/clickandbuilds/providered/wp-load.php");

    $user_login = $_GET['user_login'] ?? '';
    $user_pass = $_GET['user_pass'] ?? '';
    $user_credentials = array(
         'user_login'    => $user_login,
          'user_password' => $user_pass,
            'remember'      => false  // Opcional: Establece en true si deseas recordar la sesi贸n
    );
    $user= wp_signon($user_credentials, false);
   if (is_wp_error($user)) {
        echo "0";
         return;
    } else{
        echo "1";
    }


?>