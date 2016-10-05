#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <nfc/nfc.h>

/**
 * Daemon file for NFC reader (used by Yagthu ESGI project)
 * YAGTHU = You Are Going To Hate Us - student project with IoT:
 * 
 * Modified from original example @ Libnfc:
 * http://nfc-tools.org/index.php?title=Libnfc:APDU_example
 * Allows testing of Host-based Card Emulatio Android example at:
 * https://github.com/justinribeiro/android-hostcardemulation-sample
 * Requires Libnfc: http://nfc-tools.org/index.php?title=Libnfc
 * To compile: gcc -o apdu_tag_test apdu_tag_test.c -lnfc
 **/

int CardTransmit(nfc_device *pnd, uint8_t * capdu, size_t capdulen, 
                    uint8_t * rapdu, size_t * rapdulen) {
    int res;
    size_t  szPos;

    printf("=> ");
    for (szPos = 0; szPos < capdulen; szPos++) {
        printf("%02x ", capdu[szPos]);
    }

    printf("\n");

    if ((res = nfc_initiator_transceive_bytes(pnd, capdu, capdulen, rapdu, *rapdulen, 500)) < 0) {
        return -1;
    } else {
        *rapdulen = (size_t) res;
        printf("<= ");
        for (szPos = 0; szPos < *rapdulen; szPos++) {
          printf("%02x ", rapdu[szPos]);
        }

        printf("\n\n");
    }

    return 0;
}

int main(int argc, const char *argv[]) {
    
    pid_t pid, sid; // Our process ID and Session ID
    FILE *flog = NULL; // Log file

    /* Fork off the parent process */
    pid = fork();
    if (pid < 0) {
        exit(EXIT_FAILURE);
    }

    /* If we got a good PID, then
       we can exit the parent process. */
    if (pid > 0) {
        exit(EXIT_SUCCESS);
    }

    umask(0); // Change the file mode mask      
            
    /* Create a new SID for the child process */
    sid = setsid();
    if (sid < 0) {
        exit(EXIT_FAILURE);
    }

    /* Change the current working directory */
    if ((chdir("/")) < 0) {
        exit(EXIT_FAILURE); // Fail
    }

    /* Open log file */
    flog = fopen("yagthu-logs.txt", "w+");

    /* Close out the standard file descriptors */
    close(STDIN_FILENO);
    close(STDOUT_FILENO);
    close(STDERR_FILENO);

    /* Daemon-specific initialization goes here */
    nfc_device *pnd;
    nfc_target nt;
    nfc_context *context;

    /* Do the job! */
    while (1) {
        nfc_init(&context);
        fprintf(flog, "Init NFC with context.\n");
        fprintf(flog, "\nRunning checks...\n");

        if (context == NULL) {
            fprintf(flog, "Unable to init libnfc (malloc)\n");
            exit(EXIT_FAILURE);
        }

        const char *acLibnfcVersion = nfc_version();
        (void)argc;
        fprintf(flog, "%s uses libnfc %s\n", argv[0], acLibnfcVersion);

        pnd = nfc_open(context, NULL);

        if (pnd == NULL) {
            fprintf(flog, "ERROR: %s", "Unable to open NFC device.");
            exit(EXIT_FAILURE);
        }

        if (nfc_initiator_init(pnd) < 0) {
            nfc_perror(pnd, "nfc_initiator_init");
            fprintf(flog, "nfc_perror");
            exit(EXIT_FAILURE);
        }

        fprintf(flog, "NFC reader: %s opened\n", nfc_device_get_name(pnd));

        const nfc_modulation nmMifare = {
            .nmt = NMT_ISO14443A,
            .nbr = NBR_106,
        };

        // nfc_set_property_bool(pnd, NP_AUTO_ISO14443_4, true);
        fprintf(flog, "Polling for target...\n\n");
        while (nfc_initiator_select_passive_target(pnd, nmMifare, NULL, 0, &nt) <= 0);
        fprintf(flog, "Target detected! Running command set...\n");
        uint8_t capdu[264];
        size_t capdulen;
        uint8_t rapdu[264];
        size_t rapdulen;

        // Select application
        memcpy(capdu, "\x00\xA4\x04\x00\x07\xF0\x39\x41\x48\x14\x81\x00\x00", 13);
        capdulen = 13;
        rapdulen = sizeof(rapdu);

        if (CardTransmit(pnd, capdu, capdulen, rapdu, &rapdulen) < 0)
            exit(EXIT_FAILURE);

        if (rapdulen < 2 || rapdu[rapdulen-2] != 0x90 || rapdu[rapdulen-1] != 0x00)
            exit(EXIT_FAILURE);

        fprintf(flog, "Application selected!\n");
        
        // Byte[] elements (Id Room, Number Room)
        memcpy(capdu, "\x49\x64\x3a\x35\x35\x61\x65\x66\x31\x34\x36\x62\x34\x32\x37\x64\x30\x30\x33\x30\x30\x62\x39\x35\x62\x30\x35\x2d\x52\x6f\x6f\x6d\x3a\x41\x30\x37", 36);
        capdulen=36;
        rapdulen=sizeof(rapdu);

        fprintf(flog, "Sending elements to application...\n");
        if (CardTransmit(pnd, capdu, capdulen, rapdu, &rapdulen) < 0)
            exit(EXIT_FAILURE);

        //if (rapdulen < 2 || rapdu[rapdulen-2] != 0x90 || rapdu[rapdulen-1] != 0x00)
        //   exit(EXIT_FAILURE);

        fprintf(flog, "\nWrapping up, closing session.\n\n");

        nfc_close(pnd);
        nfc_exit(context);
        fprintf(flog, "Closing NFC.\n");
        fprintf(flog, "\n##############################################\n\n");

        fflush(flog);
        sleep(1);
    }

    fprintf(flog, "Daemon ends.\n");

    fclose(flog);
    exit(EXIT_SUCCESS);
}