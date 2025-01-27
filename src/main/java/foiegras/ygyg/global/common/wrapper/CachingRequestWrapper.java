package foiegras.ygyg.global.common.wrapper;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Slf4j
public class CachingRequestWrapper extends HttpServletRequestWrapper {

	private final Charset encoding;
	private byte[] rawData;


	public CachingRequestWrapper(HttpServletRequest request) {
		super(request);

		String characterEncoding = request.getCharacterEncoding();
		if (StringUtils.isEmpty(characterEncoding)) {
			characterEncoding = StandardCharsets.UTF_8.name();
		}
		this.encoding = Charset.forName(characterEncoding);

		try (InputStream inputStream = request.getInputStream()) {
			this.rawData = IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			log.error("CachingRequestWrapper Error: {}", e.getMessage());
			throw new BaseException(BaseResponseStatus.LOGGING_ERROR);
		}
	}


	@Override
	public ServletInputStream getInputStream() {
		return new CachedServletInputStream(this.rawData);
	}


	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
	}


	private static class CachedServletInputStream extends ServletInputStream {

		private final ByteArrayInputStream buffer;


		public CachedServletInputStream(byte[] contents) {
			this.buffer = new ByteArrayInputStream(contents);
		}


		@Override
		public int read() throws IOException {
			return buffer.read();
		}


		@Override
		public boolean isFinished() {
			return buffer.available() == 0;
		}


		@Override
		public boolean isReady() {
			return true;
		}


		@Override
		public void setReadListener(ReadListener listener) {
			throw new UnsupportedOperationException("not support");
		}

	}

}
