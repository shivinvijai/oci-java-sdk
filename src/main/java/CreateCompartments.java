/**
 * Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
 */
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.identity.Identity;
import com.oracle.bmc.identity.IdentityClient;
import com.oracle.bmc.identity.model.Compartment;
import com.oracle.bmc.identity.model.CreateCompartmentDetails;
import com.oracle.bmc.identity.requests.CreateCompartmentRequest;
import com.oracle.bmc.identity.requests.ListCompartmentsRequest;
import com.oracle.bmc.identity.responses.ListCompartmentsResponse;

public class CreateCompartments {

    public static void main(String[] args) throws Exception {

        // TODO: Fill in this value
        String configurationFilePath = "~/.oci/config";
        String profile = "DEFAULT";

        AuthenticationDetailsProvider provider =
                new ConfigFileAuthenticationDetailsProvider(configurationFilePath, profile);

        String compartmentName = args[0];
        String compartmentId = provider.getTenantId();
        final String tenantId = provider.getTenantId();
        Identity identityClient = new IdentityClient(provider);
        identityClient.setRegion(Region.US_ASHBURN_1);

        createCompartment(identityClient, tenantId, compartmentName);
    }

    private static Compartment createCompartment(
                Identity client, String compartmentId, String name) {
        CreateCompartmentDetails createCompartmentDetails =
                CreateCompartmentDetails.builder()
                        .compartmentId(compartmentId)
                        .name(name)
                        .description(name)
                        .build();

        Compartment compartment =
                client.createCompartment(
                                CreateCompartmentRequest.builder()
                                        .createCompartmentDetails(createCompartmentDetails)
                                        .build())
                        .getCompartment();

        if (compartment == null) {
            throw new RuntimeException(
                    "Compartment creation fails with " + createCompartmentDetails.toString());
        }
        System.out.println("Compartment " + compartment.getName() + " created successfully");
        return compartment;
    }
}