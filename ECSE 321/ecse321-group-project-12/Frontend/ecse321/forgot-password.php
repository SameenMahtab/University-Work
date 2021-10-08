<?php
session_start();
ob_start();
if(isset($_SESSION['accesDenied'])) { unset($_SESSION['accesDenied']);}

include ("class.phpmailer.php");

?>

<style>
    #imgmcgill{
        height: 200px !important;
        margin-left: 50px;
        margin-top: 30px;
    }
</style>

<!DOCTYPE html>
<html lang="en">


<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Forgot Password</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

<div class="container" style="padding-top: 12rem">

    <!-- Outer Row -->
    <div class="row justify-content-center"style="
    text-align: center;">

        <div class="col-xl-15 col-lg-18 col-md-10">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-3 d-none d-lg-block"><img src="https://upload.wikimedia.org/wikipedia/en/2/29/McGill_University_CoA.svg" class="img-fluid" id="imgmcgill"></div>
                        <div class="col-lg-8">
                            <div class="p-4">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-2">Forgot Your Password?</h1>
                                </div>
                                <form method="post" class="user">
                                    <div class="form-group">
                                        <input  type="text" name="username" class="form-control form-control-user"  aria-describedby="emailHelp" placeholder="Enter Your Username"  >
                                        <br>
                                    </div>
                                    <button name="submit" type="submit" class="btn btn-primary btn-flat m-b-15" style=" width: 100%">Reset Password</button>
                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="small" href="login.php">Login</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

</body>

</html>

<?php
if(isset($_POST['submit'])) {
    $getEmail = 'https://ecse321-group12.herokuapp.com/email/'.$_POST['username'];

    $cSession0 = curl_init();
    curl_setopt($cSession0,CURLOPT_URL,$getEmail);
    curl_setopt($cSession0,CURLOPT_RETURNTRANSFER,true);
    curl_setopt($cSession0,CURLOPT_HEADER, false);
    $result0=curl_exec($cSession0);
    curl_close($cSession0);

    $email = $result0;


    $characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    $randomPassword = '';
    $max = strlen($characters) - 1;
    for ($i = 0; $i < 12; $i++) {
        $randomPassword .= $characters[mt_rand(0, $max)];
    }
    $mail = new PHPMailer();                              // Passing `true` enables exceptions
    try {
        //Server settings
        $mail->SMTPDebug = 0;                                 // Enable verbose debug output
        $mail->isSMTP();                                      // Set mailer to use SMTP
        $mail->Host = 'smtp.live.com';  // Specify main and backup SMTP servers
        $mail->SMTPAuth = true;                               // Enable SMTP authentication
        $mail->Username = 'ecse321-group12@hotmail.com';                 // SMTP username
        $mail->Password = 'S0ftwareengineering';                           // SMTP password
        $mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
        $mail->Port = 587;                                    // TCP port to connect to
        $mail->CharSet = 'UTF-8';

        //Recipients
        $mail->setFrom('ecse321-group12@hotmail.com', 'McGill MyCoop System');
        if (isset($email)) {
            $mail->addAddress($email); // Add a recipient
        }
        //Content
        $mail->isHTML(true);                                  // Set email format to HTML
        $mail->Subject = 'New Password ';
        $mail->Body = "Your new password: " . $randomPassword;
        $mail->send();
        /* echo "<script>    swal({
                 title: \"Success\",
             text: \"Your new password is sent\",
             type: \"success\",
             confirmButtonColor: '#DD6B55',
                 confirmButtonText: 'Okay',
             });</script>";
         } catch (Exception $e) {
         echo "<script>    swal({
                 title: \"Failure\",
             text: \"There was an error during the process please report to the admin.\",
             type: \"error\",
             confirmButtonColor: '#DD6B55',
                 confirmButtonText: 'Okay',
             });</script>", $mail->ErrorInfo;
         }else echo "<script>    swal({
                 title: \"İşlem Başarısız\",
             text: \"Lütfen geçerli bir e-mail adresi girin.\",
             type: \"error\",
             confirmButtonColor: '#DD6B55',
                 confirmButtonText: 'Tamam',
             });</script>"
       */
    } catch (Exception $e) {
        echo "<script>    swal({
               title: \"Failure\",
           text: \"There was an error during the process please report to the admin.\",
           type: \"error\",
           confirmButtonColor: '#DD6B55',
               confirmButtonText: 'Okay',
           });</script>", $mail->ErrorInfo;
    }

    if(isset($email)) {

        $setPass = 'https://ecse321-group12.herokuapp.com/setPassword?Username='.$_POST['username'].'&Password='.$randomPassword;

        // $_POST['addDoc'] = $addDoc;

        $cSession = curl_init();
        curl_setopt($cSession, CURLOPT_URL, $setPass);
        curl_setopt($cSession, CURLOPT_POST, 1); // add this line for post method
        curl_setopt($cSession, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($cSession, CURLOPT_HEADER, false);
        $result = curl_exec($cSession);
        curl_close($cSession);
    }else {
        print "Error in the username section";
    }

}
?>
