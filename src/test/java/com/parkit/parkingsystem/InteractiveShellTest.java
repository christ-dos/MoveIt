package com.parkit.parkingsystem;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {

	private static App app;

	@Mock
	private static ParkingService parkingService;

	private static InputReaderUtil inputReaderUtil;

	@Mock
	private static InteractiveShell interactiveShell;

	@Test
	@Disabled
	public static void loadInterfacetest() {
		// GIVEN

		// InputReaderUtil inputReaderUtil = new InputReaderUtil();
		// ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
		// TicketDAO ticketDAO = new TicketDAO();
		// ParkingService parkingService = new ParkingService(inputReaderUtil,
		// parkingSpotDAO, ticketDAO);
		// when(inputReaderUtil.readSelection()).thenReturn(1);

		// WHEN
		// App.main(null);
		// inputReaderUtil.readSelection();
		// InteractiveShell.loadInterface();

	}

}
