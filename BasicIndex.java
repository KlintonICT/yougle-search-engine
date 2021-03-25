
					/***********************************************************************
					 * Member1: Dujnapa Tanundet			ID: 6088105			Section: 1 *
					 * Member2:	Klinton Chhun				ID:	6088111			Section: 1 *
					 * Member3:	Arada Puengmongkolchaikit	ID:	6088133			Section: 1 *
					 ***********************************************************************/

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;


public class BasicIndex implements BaseIndex {

	@Override
	public PostingList readPosting(FileChannel fc) {
		/*
		 * TODO: Your code here
		 *       Read and return the postings list from the given file.
		 */
		PostingList postingList = null;
		ByteBuffer bf = ByteBuffer.allocate(8);
		try {
			if(fc.position() >= fc.size()) return null;
			fc.read(bf);
			bf.flip();
			postingList = new PostingList(bf.getInt()); 
			int docIdSize = bf.getInt();
			ByteBuffer docId = ByteBuffer.allocate(docIdSize*4);
			fc.read(docId);
			docId.flip();
			for(int i = 0; i < docIdSize; i++) postingList.getList().add(docId.getInt());
		}catch(Exception error) {
			System.out.println("Read Posting Error: " + error);
			error.printStackTrace();
		}
		return postingList;
	}

	@Override
	public void writePosting(FileChannel fc, PostingList p) {
		/*
		 * TODO: Your code here
		 *       Write the given postings list to the given file.
		 */
		try {
			ByteBuffer postingList = ByteBuffer.allocate(8+(p.getList().size()*4));
			postingList.putInt(p.getTermId());
			postingList.putInt(p.getList().size());
			for(int docId: p.getList()) postingList.putInt(docId);
			postingList.rewind();
			fc.write(postingList);
		}catch(Exception error) {
			System.out.println("Write Posting Error: " + error);
			error.printStackTrace();
		}
	}
}

